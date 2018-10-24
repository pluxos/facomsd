# coding: utf-8
import threading
import socket
import time
import os


HOST = 'localhost'
PORT = 10001
BUFFER_SIZE = 1024
SOCKET_CONNECTION = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #cria socket STREAM -> TCP, DGRAM -> UDP
SOCKET_CONNECTION.connect((HOST, PORT)) #conecta


def execute(command):
    global BUFFER_SIZE
    SOCKET_CONNECTION.send(command.encode())
    msg = SOCKET_CONNECTION.recv(BUFFER_SIZE).decode()
    print(msg)
    input()


# metodo automatico (sem apertar enter)
def execute2(command):
    global BUFFER_SIZE
    SOCKET_CONNECTION.send(command.encode())
    msg = SOCKET_CONNECTION.recv(BUFFER_SIZE).decode()
    print(msg)
    return msg


def run():
    global SOCKET_CONNECTION
    global HOST
    global PORT
    # CRUD OK
    print("CRUD OK (aperte enter após cada teste)")
    execute("insert/1/1")
    execute("select/1/")
    execute("update/1/2")
    execute("select/1/")
    execute("delete/1/")
    # CRUD NOK
    print("CRUD NOK (aperte enter após cada teste)")
    execute("insert/2/1")   # nao existente
    execute("insert/2/1")
    execute("select/50/")    # nao existente
    execute("update/1050/2")  # nao existente
    execute("delete/1005/")
    # Recuperando estado
    print("Recuperando estado \n")
    i=0;
    startId = 1010

    for i in range(5):
        number = startId + i # apenas um offset para evitar conflitos de id
        execute2("insert/"+str(number)+"/Item"+str(number))
    SOCKET_CONNECTION.close()
    input("Reinicie o programa do servidor e aperte [enter] aqui para continuar os testes...")
    # recria o socket e reconecta
    SOCKET_CONNECTION = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    SOCKET_CONNECTION.connect((HOST, PORT))

    startId += 5
    for i in range(5):
        number = startId + i # apenas um offset para evitar conflitos de id
        execute2("insert/"+str(number)+"/Item"+str(number))

    # Ordem de Execução
    print("Ordem de Execução")
    execute("insert/0/1") # o registro a0 é o 0 começa com o valor "1"
    for i in range(1, 1001):
        v = int(execute2("select/"+str(i-1)+'/')) + 1
        execute2("insert/"+ str(i) + "/" + str(v))
    print(str(i) + " Itens inseridos \n$ select a1000:")
    execute("select/1000/")


run()
