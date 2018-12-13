import grpc
import map_pb2_grpc
import service
import sys
import grpc_config
import threading
import queue
from math import ceil
from time import sleep, time
from concurrent import futures
from logger import logMessage, recoverFromSnapshot, recover_database_from_snap_and_log, saveSnapshot
import os

serverId = int(sys.argv[1])
nServers = len(grpc_config.SERVIDORES)

LOG_INDEX = 0
LOG_FOLDER_PATH = "./logs/log_" + str(serverId)
LOG_PATH = LOG_FOLDER_PATH + '/log.' + str(LOG_INDEX) + ".log"


def update_log_path():
    return LOG_FOLDER_PATH + '/log.' + str(LOG_INDEX) + ".log"


SNAP_INDEX = 1
SNAP_FOLDER_PATH = "./snapshots/snap_" + str(serverId)
SNAP_PATH = SNAP_FOLDER_PATH + '/snap.' + str(SNAP_INDEX) + ".snap"


def update_snap_path():
    return SNAP_FOLDER_PATH + '/snap.' + str(SNAP_INDEX) + ".snap"


SERVER_PORT = grpc_config.SERVIDORES[serverId]
SERVER_NEIGHBORHOOD = {}


F1 = queue.Queue()  # Lista de entrada.
F2 = queue.Queue()  # Lista para o Log.
F3 = queue.Queue()  # Lista para o banco de dados.
F4 = queue.Queue()  # Lista para encaminhamento.
F5 = queue.Queue()  # Lista de resolvidos

DB = recover_database_from_snap_and_log(SNAP_FOLDER_PATH, LOG_FOLDER_PATH)
print(DB)


FROM_CODE_TO_COMMAND = {
    '1': service.create,
    '2': service.retrieve,
    '3': service.update,
    '4': service.delete
}


for i in range(5):
    if i is not int(serverId):
        SERVER_NEIGHBORHOOD[str(i)] = grpc_config.SERVIDORES[i]


def next_server(key):
    for neighbour in SERVER_NEIGHBORHOOD:
        if check_key(key, neighbour):
            print('The key belongs to the server ' + neighbour)
            return neighbour


def hand_over_to_next_server():
    while True:
        # print("    Thread handOverToNextServer ON")
        message = F4.get()

        next_key = int(next_server(message.data.key))
        server_port = grpc_config.SERVIDORES[next_key]
        channel = grpc.insecure_channel(server_port)
        stub = map_pb2_grpc.MapStub(channel)
        command = message.command

        # switch
        resposta = None
        if command is '1':
            resposta = stub.Create(message)
        elif command is '2':
            resposta = stub.Retrieve(message)
        elif command is '3':
            resposta = stub.Update(message)
        elif command is '4':
            resposta = stub.Delete(message)
        F5.put({
            'id': message.id,
            'response': resposta
        })


def check_key(key, server_id):
    inf = int(server_id) * ceil((2 ** grpc_config.M_KEYS) / 5)
    sup = (int(server_id) + 1) * ceil((2 ** grpc_config.M_KEYS) / 5)
    if (key > inf) and (key < sup):
        return True
    else:
        return False


def wait_response(entry_id):
    while True:
        # print('Wainting for response. Id: ' + str(entry_id) + '\n\n')
        elem = F5.get()
        # print(elem)
        if entry_id == elem['id']:
            print(entry_id)
            # print('Returning Response: ' + str(elem))
            response = elem['response']
            print('Database: ' + str(DB))
            return response
        else:
            F5.put(elem)
            sleep(1)


def choose_queue():
    while True:
        # print("    Thread choose_queue ON")
        mensagem = F1.get()
        # print('Inserindo mensagem ' + str(mensagem) + ' na fila F2.\n\n')
        F3.put(mensagem)
        if mensagem.command in ['1', '3', '4']:
            # print('Inserindo mensagem ' + str(mensagem) + ' na fila F3.\n\n')
            F2.put(mensagem)

# Atualiza os snapshots e os logs
def refresh_log_snap():
    print('refresh_snap')
    try:
        while True:
            global SNAP_INDEX
            global LOG_INDEX
            global DB
            global LOG_FOLDER_PATH
            global SNAP_FOLDER_PATH
            global SNAP_PATH
            global LOG_PATH
            sleep(10)

            logs = os.listdir(LOG_FOLDER_PATH)
            for log in logs:
                if get_index(log) < LOG_INDEX - 3 :
                    os.remove(LOG_FOLDER_PATH + '/' + log)

            LOG_INDEX += 1
            LOG_PATH = update_log_path()
            snapshots = os.listdir(SNAP_FOLDER_PATH)

            for snap in snapshots:
                if get_index(snap) <= SNAP_INDEX - 3:
                    os.remove(SNAP_FOLDER_PATH + '/' + snap)

            saveSnapshot(DB, SNAP_PATH)
            SNAP_INDEX += 1
            SNAP_PATH = update_snap_path()

    except KeyboardInterrupt:
        print('Finalizado!')


# Pega o valor do indice a partir do path passado
def get_index(path):
    return int(path.split('.')[1])


# Tira da fila F2 e registra no log
def log_handle():
    while True:
        print('    Thread log_handle ON')
        command = F2.get()
        print('Registrando comando ' + str(command) + ' no Log...\n\n')
        logMessage(command, LOG_PATH)


def database_handle():
    print('SNAP_INDEX: ')
    print(SNAP_INDEX)
    while True:
        # print("    Thread database_handle ON")
        msg = F3.get()
        if msg.command is '0':
            print('Um cliente se desconectou.')
        else:
            command = msg.command
            data = msg.data.value
            key = msg.data.key
            resposta = FROM_CODE_TO_COMMAND[command](DB, key, data)
            print('Resposta gerada: ' + str(resposta) + '.\n\n')
            F5.put({
                'id': msg.id,
                'response': resposta
            })


def print_estado_inicial():
    print('  Server ' + str(serverId))
    print('  Vizinhos: ' + str(SERVER_NEIGHBORHOOD))
    print('  Log Index: ' + str(LOG_INDEX))
    print('  Snap Index: ' + str(SNAP_INDEX))
    print('  Log Path: ' + str(LOG_PATH))
    print('  Snapshot Path: ' + str(SNAP_PATH))
    print('  Database: ' + str(DB))


print_estado_inicial()

threading.Thread(target=database_handle, args=()).start()
threading.Thread(target=choose_queue, args=()).start()
threading.Thread(target=hand_over_to_next_server, args=()).start()
threading.Thread(target=log_handle, args=()).start()
threading.Thread(target=refresh_log_snap, args=()).start()


class MapServicer(map_pb2_grpc.MapServicer):
    def LogTest(self, request, context):
        return 0

    def Create(self, request, context):
        request.command = '1'
        if check_key(request.data.key, serverId):
            F1.put(request)
        else:
            F4.put(request)
        print('Database: ' + str(DB))
        return wait_response(request.id)

    def Retrieve(self, request, context):
        request.command = '2'
        if check_key(request.data.key, serverId):
            F1.put(request)
        else:
            F4.put(request)
        print('request.id:' + str(request.id))
        return wait_response(request.id)

    def Update(self, request, context):
        request.command = '3'
        if check_key(request.data.key, serverId):
            F1.put(request)
        else:
            F4.put(request)
        print('Database: ' + str(DB))
        return wait_response(request.id)

    def Delete(self, request, context):
        request.command = '4'
        if check_key(request.data.key, serverId):
            F1.put(request)
        else:
            F4.put(request)
        print('Database: ' + str(DB))
        return wait_response(request.id)


server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))

map_pb2_grpc.add_MapServicer_to_server(
    MapServicer(), server)

print('Starting server. Listening on ' + SERVER_PORT + '\n')
server.add_insecure_port(SERVER_PORT)
server.start()
