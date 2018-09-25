import socket
import sys
import traceback
from threading import Thread
import fila
import banco
import os

try: # abre arquivo de configuracao, inicia as filas, banco e socket
    config = open("config.cfg","r")
    host = config.readline().split()
    host = host[1]
    port = config.readline().split()
    port = int(port[1])
    maxclients = config.readline().split()
    maxclients = int(maxclients[1])
    config.close()
    f1 = fila.Fila()
    f2 = fila.Fila()
    f3 = fila.Fila()
    bd = banco.Banco()
    soc = socket.socket()
    soc.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
except:
    print("Erro ao configurar servidor")
    exit()

def main():
    if os.path.isfile("log.txt"): #verifica se arquivo de log existe
        recupera_memoria() # se existir recupera o estado anterior        
    try:
        soc.bind((host, port))
        soc.listen(maxclients)
    except:
        print("Erro bind")
        exit()
    print("Aguardando conexão...")
    inicia_server()

def recupera_memoria(): #abre arquivo de log e restaura a memoria
    try:
        with open("log.txt") as log:
            ativo = True
            while ativo:
                linha = log.readline().split(' ',2)
                if not linha[0]:
                    ativo = False
                    break           
                if int(linha[0]) == 1:
                    bd.create(int(linha[1]),linha[2].strip("\n"))
                elif int(linha[0]) == 3:
                    bd.update(int(linha[1]),linha[2].strip("\n"))
                elif int(linha[0]) == 4:
                    bd.delete(int(linha[1]))               
            print(bd.dicionario) #
        log.close()
    except:
        print("Erro ao abrir arquivo para recuperar memoria")

def inicia_server(): #conecta ao cliente e inicia thread para receber requisicoes
    cont  = 0
    ativo = True
    while ativo:
        conexao, endereco = soc.accept()
        cont += 1
        print("Conectado com: " + str(endereco[0]) + ":" + str(endereco[1]))
        try:
            c = Thread(target=client_thread, name="client", daemon = True, args=(conexao, endereco, cont))
            c.start()
            c.join()
        except:
            print("client_tread não iniciou.")
            traceback.print_exc()
            ativo = False
            soc.close()


def encerra_server(): # se todos os clientes se desconectarem deleta o arquivo de log
    print("Deseja exluir o arquivo de log?")
    entrada = input("Y/N: ")
    if entrada =="Y" or entrada =="y":
        if os.path.isfile("log.txt"): #verifica se arquivo de log existe e o remove
            os.remove("log.txt")
    soc.close()

def client_thread(conexao, endereco, cont): #recebe as requisicoes dos clientes e inicia thread para duplicar a fila
    ativo = True
    while ativo:
        requisicao = conexao.recv(1024).decode("utf8")
        temp = requisicao.split(' ',2)
        if int(temp[0]) == 5:
            ativo = False
            print("Conexão: " + str(endereco[0]) + ":" + str(endereco[1]) + " fechada")
            cont -= 1
            if cont == 0:
                encerra_server() #se todos os clientes encerrarem a conexao o servidor encerra
                ativo = False 
            conexao.close()
        else:            
            try:
                f1.insere(requisicao)
                d = Thread(target=duplica_thread, name="duplica", args=(conexao, endereco))
                d.start()
                d.join()
            except:
                print("Thread did not start.")
                ativo = False
    ativo = False

def duplica_thread(conexao, endereco):# remove as requisicoes de f1 e duplica para f2 e f3 e inicia as threads de log e banco
    ativo = True
    while ativo:
        while not f1.vazia():
            comando = f1.retira()
            f2.insere(comando)
            f3.insere(comando)
            try:
                l = Thread(target=log_thread, name="log",args=(conexao, endereco))
                d = Thread(target=banco_thread, name="database",args=(conexao, endereco))
                l.start()                
                d.start()
            except:
                print("log_thread não iniciou.")
                traceback.print_exc()
        ativo = False

def log_thread(conexao, endereco): #grava as requisicoes em arquivo de log
    ativo = True    
    log = open("log.txt","a")
    while ativo:
        while not f2.vazia():
            requisicao = str(f2.retira())
            cm = requisicao.split(' ',2)
            if int(cm[0]) != 2 : # se for diferente de Read 
                log.writelines(requisicao +"\n")                
        ativo = False


def banco_thread(conexao, endereco): # grava as requisicoes em banco de dados
    ativo = True
    while ativo:
        while not f3.vazia():
            cm = str(f3.retira())
            cm = cm.split(' ',2)
            ok = False
            if int(cm[0]) == 1 :
                if bd.create(int(cm[1]),cm[2]):
                    conexao.sendall("OK".encode("utf8"))
                    ok = True
            elif int(cm[0]) == 2 :
                    read = bd.read(int(cm[1]))
                    if read:
                        conexao.sendall(("Chave: " + str(cm[1]) +" Valor: " + read).encode("utf8"))
                        ok = True
            elif int(cm[0]) == 3 :
                if bd.update(int(cm[1]),cm[2]):
                    conexao.sendall("OK".encode("utf8"))
                    ok = True
            elif int(cm[0]) == 4 :
                if bd.delete(int(cm[1])):
                    conexao.sendall("OK".encode("utf8"))
                    ok = True
            if not ok:
                conexao.sendall("NOK".encode("utf8"))
        ativo = False

main()