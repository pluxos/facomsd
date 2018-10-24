# coding: utf-8
from threading import Thread, Event
import socket
import sys
import time

HOST = 'localhost'
PORT = 10001
BUFFER_SIZE = 1024
EVENT = Event()
SOCKET_CONN = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
SOCKET_CONN.connect((HOST, PORT))
LOCK = False


def show_menu():
    print('Digite um dos comandos a seguir:\n')
    print('[1] - Listar registros')
    print('[2] - Adicionar registro')
    print('[3] - Atualizar registro')
    print('[4] - Excluir registro')
    print('[5] - Sair')


def read_info(command):
    key = ''
    value = ''
    instruction = ''

    if command == '1':
        key = input('Digite a chave a ser lida: ')
        instruction = 'select'

    elif command == '2':
        key = input('Digite a chave a ser inserida: ')
        value = input('Digite o valor a ser inserido: ')
        instruction = 'insert'
    elif command == '3':
        key = input('Digite a chave a ser alterada: ')
        value = input('Digite o novo valor: ')
        instruction = 'update'
    elif command == '4':
        key = input('Digite a chave a ser removida: ')
        instruction = 'delete'

    return (instruction, key, value)


def receiver():
    global LOCK
    while not EVENT.isSet():
        msg = SOCKET_CONN.recv(BUFFER_SIZE).decode()  # 1024 é a quantidade de bytes que vai ser lido
        if not msg:  # se o servidor cair ele retorna uma msg vazia
            EVENT.set()  # seta o evento para true e o while pára
            break
        print(msg)
        print('\n')
        LOCK = False


def sender():
    global LOCK
    # se o evento não estiver setado
    while not EVENT.isSet():
        if not LOCK:
            show_menu()
            command = input()

            if command == "5":
                # seta o evento com true
                EVENT.set()
                break
            # verifica se é um comando válido
            elif command in ('1', '2', '3', '4'):
                instruction, key, value = read_info(command)
                # envia o comando para o servidor através do socket
                SOCKET_CONN.send('{0}/{1}/{2}'.format(instruction, key, value).encode())
                LOCK = True
            else:
                print("Comando inválido, tente novamente...")


if __name__ == "__main__":
    # Verifica se a execução do python começou nesse arquivo

    t1 = Thread(target=receiver)
    t1.setDaemon(True)
    t1.start()

    t2 = Thread(target=sender)
    t2.setDaemon(True)
    t2.start()

    t2.join()

    if t1.isAlive():
        time.sleep(5)
        print("Encerrando execução.")
    else:
        print("Servidor desconectado... Encerrando execução.")
