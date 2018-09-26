import socket as sk
from threading import Thread
import time
from configurar import Config
import sys



def mostra_menu(client) :
    time.sleep(1)
    while True:
        print("-----MENU-------\n")
        print("1 - create data")
        print("2 - search data")
        print("3 - update data")
        print("4 - delete data")
        print("5 - quit system")
        resposta = input("Digite a opcao:")
        resposta = resposta.lower()
##        sys.stdin.flush()
        if resposta == "sair" or resposta == '5':
            print("saindo...")
            time.sleep(20)
            client.send("sair".encode())
            break
        
        elif resposta == 'create' or resposta == '1':
            key = input("Digite a chave:")
            aaa = list(key)
            key = aaa[0]
            if not key.isnumeric() :
                print("A chave deve ser um número inteiro!")
            else:
                value = input("Digite os dados:")
                envia_dados(client,"1",key,value)
            
        elif resposta == 'read' or resposta == '2':
            key = input("Digite a chave:")
            if not key.isnumeric() :
                print("A chave deve ser um número inteiro!")
            else:
                envia_dados(client,"2",key)
            
        elif resposta == 'update' or resposta == '3':
            key = input("Digite a chave:")
            if not key.isnumeric() :
                print("A chave deve ser um número inteiro!")
            else:  
                value = input("Digite o novo dado:")
                envia_dados(client,"3",key,value)
                
        elif resposta == 'delete' or resposta == '4':
            key = input("Digite a chave:")
            if not key.isnumeric() :
                print("A chave deve ser um número inteiro!")
            else :
                envia_dados(client,"4",key)
        else :
            print("Opcao Invalida")





def recebe_dados(client):
    print("Aguardando dados:")
    while True:
        
        msg = client.recv(1024).decode()
        if not msg :
            client.close()
            break
        else:
            print("log:",msg)
        
            

def envia_dados(client,operation,key,value=""):

    data = operation + ":::" + key + ":::" + value
    client.send(data.encode())
    



##def fecha_conexao(client):
##    t0 = 
    

def run():
    config = Config()
    connection = config.set_environment()
    socket_client = sk.socket()
    socket_client.connect(connection)
    thread1 = Thread(target=mostra_menu, args=(socket_client,))
    thread2 = Thread(target=recebe_dados, args=(socket_client,))
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join(5)



def run_teste(arquivo):
    config = Config()
    connection = config.set_environment()
    socket_client = sk.socket()
    socket_client.connect(connection)
    thread2 = Thread(target=recebe_dados, args=(socket_client,))
    thread2.start()


    tester = open(arquivo, 'r')
    dados = tester.read()
    tester.close()
    dados = dados.split("\n")
    dados.pop() # para retirar o caractere de final de arquivo

    for linha in dados:
        if linha == 'sair':
            time.sleep(5)
            socket_client.send(linha.encode())
            break
        else:
            aux = ""
            opcao = ""
            valor = ""
            aux = linha.split(" ")
            opcao = aux.pop(0)
            chave = aux.pop(0)
            valor = " ".join(aux)
            envia_dados(socket_client,opcao,chave,valor)
            time.sleep(0.005)

    
        

    


    
if __name__ == "__main__":
    if len(sys.argv) == 1:
        run()

    else:
        run_teste(sys.argv[1])
    
    
    











    #thread1 = Thread(target=mostra_menu, args=(socket_client,))
    #thread1.start()
    #thread2.start()
    #thread1.join()
    #thread2.join(5)







    

