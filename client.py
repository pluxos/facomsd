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
    ativo = True
    print("1- Create Ex: 1 123 456789")
    print("2- Read   Ex: 2 123")
    print("3- Update Ex: 3 123 000000")
    print("4- Delete Ex: 4 123")
    print("5- Sair   Ex: 5")
    while ativo:
        entrada = input("Comando: ")
        temp = entrada.split(' ',2)
        while temp[0] != '5':
            if validar_temp(entrada):
                soc.send(entrada.encode("utf8"))
                try:
                    r = threading.Thread(target=resposta_thread, name="resposta")
                    r.start()
                    r.join()
                except:
                    print("Erro ao iniciar resposta_thread")
            else:
                print("Comando não reconhecido")
            entrada = input("Comando: ")
            temp = entrada.split(' ',2)
        soc.send("5".encode("utf8"))
        ativo = False

def validar_temp(entrada): # valida se os temp estao corretos
    msg = entrada.split(' ',2)  
    try:
        if int(msg[0]) >= 1 and int(msg[0]) <= 5 and isinstance(int(msg[1]),int) :          
            return True
        else:
            return False
    except:
        return False

def resposta_thread(): # aguarda a resposta do servidor e imprime o retorno
    msg = soc.recv(1024).decode()
    print(msg)

main()