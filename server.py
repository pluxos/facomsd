# coding: utf-8
from threading import Thread, Event
import socket
import queue
import time
import os


HOST = 'localhost'
PORT = 10001
BUFFER_SIZE = 1024
EVENT = Event()
SOCKET_CONN = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
SOCKET_CONN.bind((HOST, PORT))
SOCKET_CONN.listen()

INPUT_QUEUE = queue.Queue(maxsize=-1)
PROCESS_QUEUE = queue.Queue(maxsize=-1)
LOG_QUEUE = queue.Queue(maxsize=-1)
hashtable = {}

def reload_hash():
    try:
        with open('db.txt', 'r') as file:
            i = 0
            for line in file:
                line.replace('\n', '')
                i += 1
                process_command(reload=True, data=line)
            print('{0} instruções recuperadas'.format(i))
    except:
        print('Erro ao tentar recarregar a tabela hash')


def receive_command(connection, address):
    global EVENT
    global INPUT_QUEUE
    global BUFFER_SIZE

    print('Nova conexão estabelecida:', address)
    while not EVENT.is_set():
        data = connection.recv(BUFFER_SIZE).decode()
        if not data:
            break
        INPUT_QUEUE.put((connection, address, data))

    connection.close()


def enqueue_command():
    global EVENT
    global INPUT_QUEUE
    global PROCESS_QUEUE
    global LOG_QUEUE

    while not EVENT.is_set():
        if not INPUT_QUEUE.empty():
            connection, address, data = INPUT_QUEUE.get()
            PROCESS_QUEUE.put((connection, data))
            LOG_QUEUE.put((address, data))


def process_command(reload=False, data=""):
    global EVENT
    while not EVENT.is_set():
        if not PROCESS_QUEUE.empty() or reload:
            if not reload:
                connection, data = PROCESS_QUEUE.get()

            print(data)
            query = data.split('/')
            command = query[0]
            key = int(query[1])
            received_data = " ".join(map(str, query[2:])) if len(query) > 2 else ""

            response = ''

            if command == 'insert':
                if key not in list(hashtable.keys()):
                    hashtable[key] = received_data
                    response = 'Chave {0} inserida com valor: {1}'.format(key, received_data).encode()
                else:
                    response = 'Já existe um valor para a chave {0}'.format(key).encode()

            elif command == 'update':
                if key in list(hashtable.keys()):
                    hashtable[key] = received_data
                    response = "Chave {0} atualizada com valor: {1}".format(key, received_data).encode()
                else:
                    response = 'A chave {0} não foi encontrada no banco de dados'.format(key).encode()

            elif command == 'delete':
                if key in list(hashtable.keys()):
                    hashtable.pop(key, None)
                    response = 'Chave {0} removida com sucesso'.format(key).encode()
                else:
                    response = 'A chave {0} não foi encontrada no banco de dados'.format(key).encode()

            elif command == 'select':
                if key in list(hashtable.keys()):
                    response = str(hashtable[key]).encode()
                else:
                    response = 'A chave {0} não foi encontrada no banco de dados'.format(key).encode()

            else:
                response = 'Comando inválido'.encode()

            if not reload:
                try:
                    connection.send(response)
                except:
                    print('Erro ao tentar enviar a seguinte mensagem para o cliente: ' + response)

            else:
                break


def log_command():
    global EVENT
    global LOG_QUEUE
    logfile = open('db.txt', 'a')
    while not EVENT.is_set():
        if not LOG_QUEUE.empty():
            address, data = LOG_QUEUE.get()
            if data.split('/') != 'select':
                logfile.write(data + '\n')
                logfile.flush()

    logfile.close()


def program_loop():
    print('Servidor recebendo conexões!')
    while True:
        try:
            connection, address = SOCKET_CONN.accept()
            t = Thread(target=receive_command, args=(connection, address))
            t.setDaemon(True)
            t.start()
        except KeyboardInterrupt:
            EVENT.set()
            print('Desligando servidor...')
            time.sleep(5)
            SOCKET_CONN.close()
            break

def run():
    reload_hash()

    t1 = Thread(target=enqueue_command)
    t1.setDaemon(True)
    t1.start()

    t2 = Thread(target=process_command)
    t2.setDaemon(True)
    t2.start()

    t3 = Thread(target=log_command)
    t3.setDaemon(True)
    t3.start()

    program_loop()

if __name__ == '__main__':
    run()
