from threading import Thread
from configurar import Config
import time
import queue
import grpc
from concurrent import futures
import definition_pb2
import definition_pb2_grpc
import pickle
import os
import sys

_TEMPO_DE_SNAP = 30
meuID = int()
threads = futures.ThreadPoolExecutor(max_workers=15)
database = {}
fila = []
fila1 = queue.Queue()
fila2 = queue.Queue()
fila3 = queue.Queue()
fila4 = queue.Queue()
tabela_roteamento = {}
chave_responsabilidade = ()
num_snap = 0
log_atual = str()
global handler_global
writePermission = True
global max_chave

##############################################################################    
class Operacao(definition_pb2_grpc.OperacaoServicer):
    
    def Create(self,request,context):
        key = request.chave
        data = request.dados
        requisicao = "1:::"+key+":::"+data
        fila_auxiliar = queue.Queue()
        fila1.put([requisicao,fila_auxiliar])
        rsp = fila_auxiliar.get()
        del fila_auxiliar
        return definition_pb2.ResultReply(resposta=rsp)

    def Read(self, request, context):
        key = request.chave
        requisicao = "2:::"+key
        fila_auxiliar = queue.Queue()
        fila1.put([requisicao,fila_auxiliar])
        rsp = fila_auxiliar.get()
        del fila_auxiliar
        return definition_pb2.ResultReply(resposta=rsp)

    def Update(self, request, context):
        key = request.chave
        data = request.dados
        requisicao = "3:::"+key+":::"+data
        fila_auxiliar = queue.Queue()
        fila1.put([requisicao,fila_auxiliar])
        rsp = fila_auxiliar.get()
        del fila_auxiliar
        return definition_pb2.ResultReply(resposta=rsp)

    def Delete(self, request, context):
        key = request.chave
        requisicao = "4:::"+key
        fila_auxiliar = queue.Queue()
        fila1.put([requisicao,fila_auxiliar])
        rsp = fila_auxiliar.get()
        del fila_auxiliar
        return definition_pb2.ResultReply(resposta=rsp)

##############################################################################


    

def start_server(num_server):
    
    global log_atual
    global handler_global
    global meuID
    global tabela_roteamento
    global chave_responsabilidade
    global max_chave

    conf = Config()

    configuracao = conf.conectar(num_server)
    if configuracao == None:
        print("erro na inicialização do servidor: numero maximo atingido")
        return -1

    connection                = configuracao[0]
    ID_SERVER                 = configuracao[1]
    tabela_roteamento         = configuracao[2]
    espectro_responsabilidade = configuracao[3]
    max_chave                 = configuracao[4]
    
    meuID = ID_SERVER
    chave_responsabilidade = (ID_SERVER, ID_SERVER - espectro_responsabilidade)
    
    print(chave_responsabilidade)
    print(tabela_roteamento)
    print("Inicializando servidor")
    
    recupera_dados()
    
    handler_global = open(log_atual, "a")

    server = grpc.server(threads)
    definition_pb2_grpc.add_OperacaoServicer_to_server(Operacao(),server)
    server.add_insecure_port(connection)
    server.start()

    Thread(target=duplica_filas).start()
    Thread(target=fila_arquivo).start()
    Thread(target=fila_dicionario).start()
    Thread(target=trata_fila4).start()
    Thread(target=cria_snapshot).start()

    print("Server set, ready to accept connections")
    
    try:
        while True:
            time.sleep(86400)
    except KeyboardInterrupt:
        handler_global.close()
        server.stop(0)



    
###############################################################################


def duplica_filas():

    while True:
        
        aux = fila1.get()
        chave = aux[0].split(":::")
        chave = int(chave[1])
        if (chave <= chave_responsabilidade[0]) and (chave > chave_responsabilidade[1]):
            
            fila2.put(aux)
            fila3.put(aux)
        else :
            fila4.put(aux)        


###############################################################################                  
            
def fila_arquivo():
    global log_atual
    global handler_global
    global writePermission
    
    
    while True:
            aux = ""
            aux = fila2.get()
            print("Eu tratei essa requisicao", aux[0])     
            auxiliar = trataFila2(aux[0]) #passa para ser tratada, somente a requisição, a fila é ignorada.    
            if writePermission:
                time.sleep(0.1)        
            
            handler_global.write(auxiliar)
            handler_global.flush()                 
    
##############################################################################                
                
def trataFila2(aux2):
    aux = str(aux2)
    if aux[0] == '2':
        aux = ""
        return aux
    else:
        aux = aux + "\n"
        return aux


##############################################################################    

def fila_dicionario():
    while True:
        
            aux = ""
            aux = fila3.get()
            trataFila3(aux)

#############################################################################

def trataFila3(aux3):
    
    #aux3 é uma lista contendo [requisição,fila_auxiliar] / requisição é do tipo: "OPERACAO:::CHAVE:::DADOS" e a fila auxiliar é do tipo queue.Queue

    resposta = ""
    resposta = aplica_no_banco(aux3[0])                                         #aplica requisição no banco de dados
    aux3[1].put(resposta)                                                      #devolve a resposta para o skeleton responder o cliente
            
##############################################################################        

def aplica_no_banco(aux):
    
    resposta = "vacilos maker mano\n"
    global database
    if aux == "":
        return resposta
    aux1 = aux.split(":::")
    operacao = int(aux1[0])
    aux1.pop(0)
    chave = int(aux1[0])
    aux1.pop(0)
    dados = ":::".join(aux1)

    if operacao == 1:
        if chave in database:
            resposta = "NOK - Chave ja existe no banco de dados"
        else :
            database[chave] = dados
            resposta = "OK - Armazenado com sucesso"
    elif operacao == 2:
        if chave in database:
            resposta = "OK - Dados:" + database[chave]
        else :
            resposta = "NOK - Chave nao existe no banco de dados"
    elif operacao == 3:
        if chave in database:
            database[chave] = dados
            resposta = "OK - Atualizado com sucesso"
        else :
            resposta = "NOK - Chave nao existe no banco de dados"
    elif operacao == 4:
        if chave in database:
            del database[chave]
            resposta = "OK - Deletado com sucesso"
        else :
            resposta = "NOK - Chave nao existe no bando de dados"
    else :
        resposta = "NOK - Operacão nao suportada"

    return resposta
    

##############################################################################


def recupera_dados():
    
    #handler = man
    global num_snap
    global database
    global meuID
    global log_atual
    snap = encontra_snap()
    log  = encontra_log()

    if snap != None:
        rec = "./snaps/" + snap[0]
        leitor =  open(rec,"rb")
        dados = leitor.read()
        leitor.close()
        database = pickle.loads(dados)
        log_auxiliar = log[1]
        diretorio_auxiliar = log[0].split(".")
        diretorio_auxiliar = (diretorio_auxiliar[0] + "." + diretorio_auxiliar[1] + ".")
        while log_auxiliar > snap[1]: 
            try:
                handler = open("./log/"+diretorio_auxiliar+str(log_auxiliar), "r")
                data = handler.read()
                handler.close()
            except:
                pass

            data = data.split("\n")
            
            for aux in data:
                resposta = aplica_no_banco(aux)

            log_auxiliar -= 1
        log_auxiliar += 1
        log_atual = "./log/" + str(meuID) + ".log." + str(log_auxiliar)
        num_snap = log_auxiliar
    else:
        log_atual = "./log/" + str(meuID) + ".log.0" 

        
#############################################################################
def trata_fila4():
    
    global tabela_roteamento
    global meuID
    
    anterior = tabela_roteamento[1]
    proximo = tabela_roteamento[0]
    meio = distancia_mod(meuID)
    while True:
    
        aux = fila4.get()        
        dados = aux[0].split(":::")
        chave = int(dados[1])
        
        
        if chave <= chave_responsabilidade[1]    and chave >= meio:
            channel = grpc.insecure_channel(anterior) 
            stub = definition_pb2_grpc.OperacaoStub(channel)
            resultado = repassa_requisicao(stub,dados)
            aux[1].put(resultado.resposta)
        else :
            
            channel = grpc.insecure_channel(proximo) 
            stub = definition_pb2_grpc.OperacaoStub(channel)
            resultado = repassa_requisicao(stub,dados)
            aux[1].put(resultado.resposta)
            

    

#############################################################################
def repassa_requisicao(stub,dados):
    if dados[0] == "1":
        key = dados[1]
        value = dados[2]
        response = stub.Create(definition_pb2.CreateUpdateRequest(chave=key, dados=value))
    elif dados[0] == '2':
        key = dados[1]
        response = stub.Read(definition_pb2.ReadDeleteRequest(chave=key))
    elif dados[0] == '3':
        key = dados[1]
        value = dados[2]
        response = stub.Update(definition_pb2.CreateUpdateRequest(chave=key,dados=value))
    elif dados[0] == '4':
        key = dados[1]
        response = stub.Delete(definition_pb2.ReadDeleteRequest(chave=key))
    
    return response




############################################################################

def cria_snapshot():
        
    global num_snap
    global database
    global meuID

    while True:
        time.sleep(_TEMPO_DE_SNAP)
        arquivo = "./snaps/" + str(meuID) + ".snap." + str(num_snap)
        pickle.dump(database, open(arquivo,'wb'))
        num_snap +=1
        cria_logs(meuID)
        if num_snap > 3:
            arquivo_remov = "./snaps/"  +str(meuID) + ".snap." + str(num_snap - 4)
            os.remove(arquivo_remov)
        
        
#############################################################################
def cria_logs(identificador):
    global log_atual
    global handler_global
    global num_snap
    global writePermission


    logs = "./log/" + str(identificador) +".log." + str(num_snap)
    writePermission = True
    handler_global.close()
    handler_global = open(logs,"a")
    writePermission = False
    log_atual = logs

    if num_snap > 2:
        log_remov = "./log/" + str(identificador) +".log." + str(num_snap - 3)
        os.remove(log_remov)


#############################################################################

def encontra_snap():
    global meuID
    global num_snap
    diretorio = "./snaps/"
    lista_dir = os.listdir(diretorio)
    maior = -1
    if lista_dir == None:
        print("Nenhum snapshot válido")
        return None
    for arq in lista_dir:
        aux = arq.split(".")
        try:
            if int(aux[0]) == meuID:
                if int(aux[2]) > maior:
                    maior = int(aux[2])
        except:
            pass
    if maior == -1:
        return None 

    else:
        num_snap = maior
        return str(meuID) +".snap." + str(maior), maior


#############################################################################
def encontra_log():
    diretorio = "./log/"
    lista_dir = os.listdir(diretorio)
    maior = -1
    if lista_dir == None:
        print("Nenhum diretório válido")
        return None
    for arq in lista_dir:
        aux = arq.split(".")
        try:
            if int(aux[0]) == meuID:
                if int(aux[2]) > maior:
                    maior = int(aux[2])
        except:
            pass
    if maior == -1:
        return None

    else:
        num_snap = maior
        return str(meuID) +".log." + str(maior), maior


#############################################################################

def distancia_mod(Id):
    '''calcula a metade de um circulo gerado pelo mod'''

    global max_chave

    mod = max_chave + 1
    metade = mod // 2
    meio = (Id + metade) % mod

    return meio


    


#############################################################################

if __name__ == "__main__":

    arg = sys.argv
    if len(arg) > 1:
        start_server(int(arg[1]))
    else:
        start_server(0)
    
    
    