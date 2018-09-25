import socket
import sys
import threading
import time

try: # abre arquivo de configuracao e inicializa socket
    config = open("config.cfg","r")
    host = config.readline().split()
    host = host[1]
    port = config.readline().split()
    port = int(port[1])
    soc = socket.socket()
    config.close()
except:
    print("Erro ao abrir arquivo de configuração")
    exit()

def main(): #conecta ao servidor e inicia thread de leitura de temps
    try:
        soc.connect((host, port))
    except:
        print("Erro ao conectar ao servidor")
    try:
       c = threading.Thread(target=crud_thread, name="crud")
       c.start()
    except:
        print("Erro ao iniciar crud_thread")
        soc.close()

def crud_thread(): #lê os temps, valida-os e envia ao servidor
 
    comando = "1 0 1"
    print("Criar: " + comando[2:])
    soc.send(comando.encode("utf8"))
    
    time.sleep(1)

    for x in range(1,1001):
        comando = "2 " + str(x - 1)
        print("Ler: " + comando[2:])
        soc.send(comando.encode("utf8"))
        iniciar_thread()
        print("\n")
        
        comando = "1 " + str(x) + " " + str(x + 1)
        print("Criar: " + comando[2:])
        soc.send(comando.encode("utf8"))
        iniciar_thread()
        
    comando = "2 1000" 
    print("Ler: " + comando[2:])
    soc.send(comando.encode("utf8"))
    iniciar_thread()
    
    soc.send("5".encode("utf8"))
    

def resposta_thread(): # aguarda a resposta do servidor e imprime o retorno
    msg = soc.recv(1024).decode()
    if msg !="OK" and msg!="NOK":
        print(msg)

def iniciar_thread():
    try:
        r = threading.Thread(target=resposta_thread, name="resposta")
        r.start()
        r.join()
    except:
        print("Erro ao iniciar resposta_thread")

main()