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
    #CRUD OK
    entrada = "1 1 abc"
    print("Create: chave = 1 valor = abc")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "2 1"
    print("Read: chave = 1")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "3 1 xyz"
    print("Update: chave = 1 NovoValor = xyz")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "2 1"
    print("Read: chave = 1")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "4 1"
    print("Delete: chave = 1")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()
    
    #CRUD NOK
    print("..:: CRUD NOK::..")
    time.sleep( 2 )
    entrada = "1 1 abc"
    print("Create: chave = 1 valor = abc")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "1 1 abc"
    print("Create: chave = 1 valor = abc")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "2 4"
    print("Read: chave = 4")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()
    
    entrada = "3 4 abc"
    print("Update: chave = 4 NovoValor = abc")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "1 4 klm"
    print("Create: chave = 4 valor = klm")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "2 4"
    print("Read: chave = 4")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()

    entrada = "4 4"
    print("Delete: chave = 4")
    time.sleep( 2 )
    soc.send(entrada.encode("utf8"))
    iniciar_thread()
    
    soc.send("5".encode("utf8"))
    
def resposta_thread(): # aguarda a resposta do servidor e imprime o retorno
    msg = soc.recv(1024).decode()
    print(msg)
    time.sleep( 1 )

def iniciar_thread():
    try:
        r = threading.Thread(target=resposta_thread, name="resposta")
        r.start()
        r.join()
    except:
        print("Erro ao iniciar resposta_thread")

main()