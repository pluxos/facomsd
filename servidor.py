import socket as sk
from threading import Thread
import threading
from configurar import Config
import time
import queue


connections = {}
database = {}
fila1 = queue.Queue()
fila2 = queue.Queue()
fila3 = queue.Queue()






##############################################################################    

    

def start_server():

    config = Config()    
    print("Inicializando servidor")
    recupera_dados()
##    print("Deseja criar uma nova configuração de conexão?")
##    rsp = input("s ou n:")
##    print(rsp)
##    if rsp[0].lower == 's':
##        new_port = input("Digite a nova porta:")    
##    config.config_connection("localhost","0")       #configura o arquivo de conexão
        
    connection = config.set_environment() #pega a tupla ('host',port)
    sokt = sk.socket()             #inicializa o socket 
    sokt.bind(connection)          #binda a conexão
    sokt.listen(15)                 #seta o servidor para ouvir
    
    print("Server set, ready to accept connections")
    
    thr = Thread(target=accept_connections, args=(sokt,))
    Thread(target=duplica_filas).start()
    Thread(target=fila_arquivo).start()
    Thread(target=fila_dicionario).start()
    thr.start()  # inicia o loop infinito.
    thr.join()
    sokt.close()

    
###############################################################################

def accept_connections(s):
    
    while True:
    
        cliente, addr = s.accept()  #aceita a conexao
        addr = str(addr)
        connections[addr] = cliente 
        print("Got a connection from:",addr)
        msg = "Thanks to connect, the answer is 42, dear friend\r\n"
        cliente.send(msg.encode())
        Thread(target=handle_connections, args=(addr,)).start()
        #cria uma nova thread para cada conexão

###############################################################################
        
def handle_connections(addr):
    cliente = connections[addr]
    while True:
        
        msg = cliente.recv(1024)
        if msg.decode() == "sair" :
            print("exit server:",addr)
            cliente.close()
            del connections[addr]
            
            break
            

        else:
            data = addr +"::::"+ msg.decode()
            fila1.put(data)
           
###############################################################################    

def duplica_filas():

    while True:
        
        aux = fila1.get()
        fila2.put(aux)
        fila3.put(aux)
                    


###############################################################################                  
            
def fila_arquivo():
    time.sleep(1)
    handler = open("DataBaseLog", "a") #mudar para escrita binária!!!
    while True:
        aux = ""
        aux = fila2.get()                    
        aux = trataFila2(aux)
        if aux != "":
            handler.write(aux)    
                  

##############################################################################                
                
def trataFila2(aux2):
    aux = aux2.split("::::")
    aux.pop(0) 
    aux = "::::".join(aux)
    if aux[0] == '2':
        aux = ""
        return aux
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
    resposta = ""
    aux = aux3.split("::::")
    addr = aux.pop(0)
    aux = "::::".join(aux)
    cliente = connections[addr]
    resposta = aplica_no_banco(aux)
    cliente.send(resposta.encode())
            
##############################################################################        

def aplica_no_banco(aux):
    
    resposta = "vacilos maker mano\n"
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
            resposta = "NOK - Chave não existe no banco de dados"
    elif operacao == 3:
        if chave in database:
            database[chave] = dados
            resposta = "OK - Atualizado com sucesso"
        else :
            resposta = "NOK - Chave não existe no banco de dados"
    elif operacao == 4:
        if chave in database:
            del database[chave]
            resposta = "OK - Deletado com sucesso"
        else :
            resposta = "NOK - Chave não existe no bando de dados"
    else :
        resposta = "NOK - Operacão não suportada"

    return resposta
    

##############################################################################


def recupera_dados():
    try:
        handler = open("DataBaseLog", "r")
        data = handler.read()
        handler.close()
    except:
        print("Erro no carregamento do banco de dados, reinicie o servidor")
        return
    
    data = data.split("\n")
    for aux in data:
        resposta = aplica_no_banco(aux)
        

#############################################################################

if __name__ == "__main__":
        start_server()
    
    
    
