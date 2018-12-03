import grpc
import grpcFiles.database_pb2 as database_pb2
import grpcFiles.database_pb2_grpc as database_pb2_grpc
import os
from threading import Thread
import time


class Client:

    def __init__(self):
        
        self.instantiate_dirs()
        self.get_connection()
        
        channel = grpc.insecure_channel(self.host+':'+self.port) 
        self.stub = database_pb2_grpc.DatabaseOperationStub(channel)

    def get_connection(self):

        if self.hasConfig():
            with open ("./configs/config_server", 'r') as fd:
                data = fd.read()
                data = data.split('\n')
                aux = data[0].split(':')
                self.host = aux[1]
                aux = data[1].split(':')
                self.port = aux[1]
                return True
        else:
            print("Error, missing server config")
            return False

    
    def hasConfig(self):
        '''Tests if there's a config, or create a default one'''

        file_name = "config_client"
        dir_list = os.listdir('./configs/')

        if file_name in dir_list:
            return True
        else:
            try:
                with open('./configs/config_client', 'w') as fd:
                    data = '''Host:localhost
Porta:50050'''
                    fd.write(data)
                    print("Warning - new config created")
                    return True
            except:
                print("ERROR - can't connect")
                print("Please, try again")
                return False

    def instantiate_dirs(self):
        '''If does not exists, create a dir for logs,snaps, configs'''

        dir_list = os.listdir('.')
        if 'configs' not in dir_list:
            os.mkdir('configs')

    def sendThread(self):

        while True:
            print("-----MENU-------\n")
            print("1 - create data")
            print("2 - search data")
            print("3 - update data")
            print("4 - delete data")
            print("5 - quit system")
            response = ""
            resposta = input("Digite a opcao:")
            resposta = resposta.lower()

            if resposta == "sair" or resposta == '5':
                print("saindo...")
                break
        
            elif resposta == 'create' or resposta == '1':
                key = input("Digite a chave:")
                if not key.isdecimal() :
                    print("A chave deve ser um número inteiro!")
                
                else:
                    value = input("Digite os dados:")
                    response = self.stub.Create.future(database_pb2.CrUpRequest(chave=key, dados=value))
                    response.add_done_callback(self.receiveThread)
    
            
            elif resposta == 'read' or resposta == '2':
                key = input("Digite a chave:")
                if not key.isdecimal :
                    print("A chave deve ser um número inteiro!")
                else:
                    response = self.stub.Read.future(database_pb2.RdDelRequest(chave=key))
                    response.add_done_callback(self.receiveThread)
            
            elif resposta == 'update' or resposta == '3':
                key = input("Digite a chave:")
                if not key.isdecimal() :
                    print("A chave deve ser um número inteiro!")
                else:  
                    value = input("Digite o novo dado:")
                    response = self.stub.Update.future(database_pb2.CrUpRequest(chave=key,dados=value))
                    response.add_done_callback(self.receiveThread)
                
            elif resposta == 'delete' or resposta == '4':
                key = input("Digite a chave:")
                if not key.isdecimal() :
                    print("A chave deve ser um número inteiro!")
                else :
                    response = self.stub.Delete.future(database_pb2.RdDelRequest(chave=key))
                    response.add_done_callback(self.receiveThread)
                    
            else :
                print("Opcao Invalida")
            
    
    def receiveThread(self,responseFuture):
        print(responseFuture.result())

    def run(self):
        
        masterThread = Thread(target=self.sendThread)
        
        masterThread.start()

        masterThread.join()

        time.sleep(5)
        
        print('Quit client')
        
        



        

if __name__ == '__main__':
    meu_cliente = Client()
    meu_cliente.run()
