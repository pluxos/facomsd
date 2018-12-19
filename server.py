#servidor.py
from threading import Thread
from concurrent import futures
import grpc
import operator
import grpcFiles.database_pb2_grpc as database_pb2_grpc
import grpcFiles.database_pb2 as database_pb2
import time
import queue
import collections
import os
import pickle
import sys
from pysyncobj import SyncObj, replicated, SyncObjConf
import logging
import traceback
from pysyncobj.batteries import ReplDict, ReplLockManager


class OperatorGrpc(database_pb2_grpc.DatabaseOperationServicer):

    def __init__(self,server_ref):
        self.server = server_ref

    def Create(self, request, context):
        response_list = queue.Queue()
        server_request = '1' + '/' + request.chave + '/' + request.dados
        self.server.line0.put([server_request,response_list])
        response = response_list.get()
        return database_pb2.Reply(resposta=response)

    def Read(self, request, context):
        response_list = queue.Queue()
        server_request = '2' + '/' + request.chave 
        self.server.line0.put([server_request,response_list])
        response = response_list.get()
        return database_pb2.Reply(resposta=response)

    def Update(self, request, context):
        response_list = queue.Queue()
        server_request = '3' + '/' + request.chave + '/' + request.dados
        self.server.line0.put([server_request,response_list])
        response = response_list.get()
        return database_pb2.Reply(resposta=response)

    def Delete(self, request, context):
        response_list = queue.Queue()
        server_request = '4' + '/' + request.chave 
        self.server.line0.put([server_request,response_list])
        response = response_list.get()
        return database_pb2.Reply(resposta=response)



class Servidor(SyncObj):
    '''Create start information for the server work
        mode default is 0, it means the server will work alone
        on single mode.
        if mode=1, server will connect in chord mode
        server_id is only needed when single mode is activated
        otherwise, the server_id will be assert by the order it
        joins the server. server_id default=0'''

    def __init__(self,selfNodeAddr,selfPartnerAddr):
        lockManager = ReplLockManager(autoUnlockTime=30)
        self.distDatabase = ReplDict()
        cfg = SyncObjConf(dynamicMembershipChange=True)
        super(Servidor,self).__init__(selfNodeAddr,selfPartnerAddr, consumers=[lockManager,self.distDatabase],conf=cfg)

                    
    def start_server(self,clusterId,server_id):
        
        self.clusterId = int(clusterId)
        self.id = int(server_id) + (10*clusterId)
        self.database = {}
        self.line0 = queue.Queue()
        self.line1 = queue.Queue()
        self.line2 = queue.Queue()
        self.line3 = queue.Queue()
        self.line4 = queue.Queue()
        self.auxLine = queue.Queue()
        self.auxLine2 = queue.Queue()
        self.threadPool = futures.ThreadPoolExecutor(max_workers=15)
        self.workPool = futures.ThreadPoolExecutor(max_workers=6)
        self.dir_log = 'logs'
        self.dir_snap = 'snaps'
        self.onChord = False
        self.instantiate_dirs()
        self.retrieveInformation()


        if self.get_connection():
            
            self.registerOnChord()
            
            
            self.server = grpc.server(self.threadPool)
            self.server.add_insecure_port(self.host+':'+self.port)
            print(self.host+':'+self.port)
            database_pb2_grpc.add_DatabaseOperationServicer_to_server(OperatorGrpc(self),self.server)
            
        else:
            print("Fatal - cannot start the server")
            exit(-1)

        print("Running server") #implementar as threads de cada fila aqui
        self.server.start()
        
        self.workPool.submit(self.preProcessing)
        self.workPool.submit(self.lineFour)
    
        self.workPool.submit(self.lineOne)

        self.workPool.submit(self.lineTwo)
        self.workPool.submit(self.lineThree)
        self.workPool.submit(self.createSnaps)
        try:
            time.sleep(86400)
        except KeyboardInterrupt:
            self.server.stop()

    def get_connection(self):

        if self.hasConfig():
            with open ("./configs/config_server", 'r') as fd:
                data = fd.read()
                data = data.split('\n')
                aux = data[0].split(':')
                self.host = aux[1]
                aux = data[1].split(':')
                self.port = aux[1]
                aux = data[2].split(':')
                self.num_servers = int(aux[1])
                aux = data[3].split(':')
                self.num_bits = int(aux[1])
                aux = data[4].split(':')
                self.snapTime = int(aux[1])
                return True
        else:
            print("Error, missing server config")
            return False

    
    def hasConfig(self):
        '''Tests if there's a config, or create a default one'''

        file_name = "config_server"
        dir_list = os.listdir('./configs/')

        if file_name in dir_list:
            return True
        else:
            try:
                with open('./configs/config_server', 'w') as fd:
                    data = '''Host:localhost
Porta Base:50050
Tamanho do Chord:4
Chave máxima(bits):5
Tempo de Snapshot(segundos):30'''
                    fd.write(data)
                    print("Warning - new config created")
                    return True
            except:
                print("ERROR - can't connect")
                print("Please, try again")
                return False


    def preProcessing(self):

        maximumKeyError = 'Cant store that key, maximum key is ' + str(self.maximumKey) 
        while True:

            request = self.line0.get()
            key = int(request[0].split('/')[1])

            if key > self.maximumKey:
                request[1].put(maximumKeyError)

            elif key <= self.chordId and key >= self.lowerKey:
                self.auxLine.put(request[1])
                self.difusao(request[0])
            else:
                self.auxLine2.put(request[1])
                self.difusao2(request[0])
        
        
    
    


    def lineOne(self):
        '''Duplicate requests to lines 2 and 3'''

        while True:
            request = self.line1.get()
            
            self.line2.put(request) #only pass the request, drop out the response_line
            self.line3.put(request)

            
    @replicated
    def difusao(self,request):
        self.line1.put(request)

    @replicated
    def difusao2(self,request):
        self.line4.put(request)


       


    def lineTwo(self):
        '''Apply changes to log'''

        while True:
            request = self.line2.get()
            
            if int(request[0]) != 2: #read request does not change the database status

                try:
                    self.log.write(request + '\n')
                    self.log.flush()
                except:
                    print('write exception')

            

    def lineThree(self):
        '''Apply changes to database'''
        
        while True:
            
            request = self.line3.get()
            response = self.applyDatabase(request)
            try:
                rsp = self.auxLine.get_nowait()
                rsp.put(response)
            except:
                pass
            request = ''

    def lineFour(self):
        '''Repass request to others servers in chord'''
        halfChord = ((self.chordId + ((self.maximumKey + 1) // 2)) % (self.maximumKey + 1))
        lowerKey = self.chordId - self.espectrum
        while True:
            stub = None
            channel = None
            request = self.line4.get()
            key = int(request.split('/')[1])

            if key <= lowerKey and key >= halfChord:
                try:
                    self.handleRequest(self.routingTable['prev'],request)
                except:
                    print('Error - on this request')
                
            else:
                try:
                    self.handleRequest(self.routingTable['next'],request)
                except:
                    print('Error - on this request')
            

    def handleRequest(self, stub, request):
        
        data = request.split('/')
        op = data.pop(0)
        key = data.pop(0)
        values = '/'.join(data)
        
        if   op == '1':
            try:

                response = stub.Create(database_pb2.CrUpRequest(chave=key,dados=values))
                rsp = self.auxLine2.get_nowait()
                rsp.put(response.resposta)
            except :
                pass

        elif op == '2':
            try:
                response = stub.Read(database_pb2.RdDelRequest(chave=key))
                rsp = self.auxLine2.get_nowait()
                rsp.put(response.resposta)
            except :
                pass
        elif op == '3':
            try:
                response = stub.Update(database_pb2.CrUpRequest(chave=key,dados=values))
                rsp = self.auxLine2.get_nowait()
                rsp.put(response.resposta)
            except :
                pass
        elif op == '4':
            try:
                response = stub.Delete(database_pb2.RdDelRequest(chave=key))
                rsp = self.auxLine2.get_nowait()
                rsp.put(response.resposta)
            except:
                pass
                
    


    def applyDatabase(self, request):
        '''Process data, and apply'''

        splitStr = '/'
        aux = request.split(splitStr)
        operation = int(aux.pop(0))
        key = int(aux.pop(0))
        data = splitStr.join(aux)
        answer = ''

        if operation == 1:
            if key in self.database:
                answer = 'NOK - Impossible to create (key already exists in database)'
            else:
                self.database[key] = data
                answer = 'OK  - Data stored sucessfully'
        elif operation == 2:
            if key in self.database:
                answer = 'OK  - Data = ' + self.database[key]
            else:
                answer = 'NOK - Impossible to read (key dont exist in database)'
        elif operation == 3:
            if key in self.database:
                self.database[key] = data
                answer = 'OK  - Data updated sucessfully'
            else:
                answer = 'NOK - Impossible to update (key dont exist in database)'
        elif operation == 4:
            if key in self.database:
                del self.database[key]
                answer = 'OK  - Data deleted sucessfully'
            else:
                answer = 'NOK - Impossible to delete (key dont exist in databse)'
        else:
            answer = 'NOK - Invalid Operation'
        
        return answer


    def instantiate_dirs(self):
        '''If does not exists, create a dir for logs,snaps, configs'''

        dir_list = os.listdir('.')
        if self.dir_log not in dir_list:
            os.mkdir(self.dir_log)
        if self.dir_snap not in dir_list:
            os.mkdir(self.dir_snap)
        if 'configs' not in dir_list:
            os.mkdir('configs')



    def retrieveInformation(self):
        '''On server start, search for previous stored information, and retrieve the status of the database'''

        list_logs = os.listdir(self.dir_log)
        list_snaps = os.listdir(self.dir_snap)
        list_logs = list(filter(lambda x : x.split('.')[0] == str(self.id), list_logs))
        list_snaps = list(filter(lambda x : x.split('.')[0] == str(self.id), list_snaps))

        if len(list_snaps) == 0:
            if len(list_logs) == 0:
                print('Fresh server - ok')
                self.numSnap = 0
                log_file = self.dir_log + '/' + str(self.id) + '.log.' + str(self.numSnap)
                self.log = open(log_file, 'a')
                return
            self.numSnap = -1
        
        else:
            snap = max(list_snaps, key = lambda x: int(x.split('.')[2]))
            self.numSnap = int(snap.split('.')[2])
            with open(self.dir_snap + '/' + snap, 'rb') as fd:
                data = fd.read()
                self.database = pickle.loads(data)
        applyLog = list(filter(lambda x: int(x.split('.')[2]) > self.numSnap, list_logs))
        applyLog.sort(key = lambda x: int(x.split('.')[2]))

        self.fileToMemory(applyLog)
            
        

    def fileToMemory(self,applyList):
        
        for file in applyList:
            last_file = file
            with open(self.dir_log + '/' + file, 'r') as fd:
                data = fd.read()
                data = data.split('\n')
                for sample in data:
                    if sample != '':
                        ans = self.applyDatabase(sample)
        self.log = open(self.dir_log + '/' + last_file, 'a')
        self.numSnap = int(last_file.split('.')[2])
        


    def createSnaps(self):
        '''Create snapshots and changes the log, for future retrieval of information'''
        
        while True:

            time.sleep(self.snapTime)
            snapFile = self.dir_snap + '/' + str(self.id) + '.snap.' + str(self.numSnap)
            self.numSnap += 1
            logFile = self.dir_log + '/' + str(self.id) + '.log.' + str(self.numSnap)
            self.log.close()
            try:
                self.log = open(logFile, 'a')
            except:
                print('Error - on file creation')
            try:
                pickle.dump(self.database, open(snapFile, 'wb'))
            except:
                print('Error - on snap creation')

            self.removeFile()

    def removeFile(self):
        
        if self.numSnap >= 3:
            if self.numSnap > 3:
                snapR = self.dir_snap + '/' + str(self.id) + '.snap.' + str(self.numSnap - 4)
                os.remove(snapR)
            logR = self.dir_log + '/' + str(self.id) + '.log.' + str(self.numSnap - 3)
            os.remove(logR)

    def registerOnChord(self):

        self.maximumKey = (2 ** self.num_bits) - 1
        self.espectrum = (self.maximumKey // self.num_servers)
        self.chordId = self.maximumKey - (self.espectrum * self.clusterId)

        if self.clusterId == self.num_servers - 1:
            self.lowerKey = 0
        else :
            self.lowerKey = self.chordId - self.espectrum + 1
        
        self.port = str( int(self.port)  + self.id)
        #because register is made with 0 the higher key, the lower id, bigger the key
        idPrev = (self.id + 10) % (self.num_servers*10)
        idNext = (self.id - 10) % (self.num_servers*10)

        self.routingTable = {}
        

        channel1 = grpc.insecure_channel((self.host + ':' + str(50050 + idPrev)))
        stub1 = database_pb2_grpc.DatabaseOperationStub(channel1)
        channel2 = grpc.insecure_channel((self.host + ':' + str(50050 + idNext)))
        stub2 = database_pb2_grpc.DatabaseOperationStub(channel2)

        self.routingTable['prev'] = stub1 
        self.routingTable['next'] = stub2
        

        print('On chord, keys:' ,self.chordId,',', self.lowerKey)



if __name__ == "__main__":

        num_cluster = int(input("Cluster:"))
        serv_id = int(input("Server ID:"))
        cluster = [12000,12001,12002]
        for p in range(len(cluster)):
            cluster[p] += (num_cluster*10)
        
        port = cluster.pop(serv_id)
        partners = ['localhost:%d' % int(p) for p in cluster]

        my_server = Servidor('localhost:%d' % port,partners)
        my_server.start_server(num_cluster,serv_id)
