import grpc
import map_pb2
import map_pb2_grpc
import grpc_config
import threading
import sys
from time import sleep
from service_client import get_id

serverId = int(sys.argv[1])

serverPort = grpc_config.SERVIDORES[serverId]
channel = grpc.insecure_channel(serverPort)

stub = map_pb2_grpc.MapStub(channel)

def speak():
    print('Bem Vindo!')
    while True:
        try:
            key = ' '
            value = ' '
            printMenu()
            print('Digite o seu comando: ')
            command = getCommand()

            if command == '0':
                sys.exit()
            else:
                print('Digite a chave escolhida: ')
                key = getKey()

                if command not in ['2', '4']:
                    print('Digite a sua mensagem: ')
                    value = getMessageContent()

                    if command == '1':
                        create(key, value)

                    if command == '3':
                        update(key, value)
                else:
                    if command == '2':
                        retrieve(key)

                    if command == '4':
                        if confirmMsg():
                            delete(key)

            print('Pressione [Enter] para continuar')
            input()
        except():
            print('Erro: Comando Invalido ou Servidor não existe.')


def isInt(s):
    try:
        int(s)
        return True
    except ValueError:
        return False


def getCommand():
    while True:
        try:
            command = input()
            if isInt(command):
                if int(command) in range(5):
                    break
            print('Comando inválido.')
            printMenu()
        except:
            print('Erro: Comando inválido.')
            printMenu()
    return command


def getKey():
    while True:
        try:
            key = int(input())

            if key and type(key) == int:
                break
            print('Valor invalido para chave.')
            print('Digite a chave escolhida:')
        except:
            print('Erro: Valor invalido para chave.')
            print('Digite a chave escolhida:')
    return key


def getMessageContent():
    try:
        while True:
            msg = input()
            # msg = 'blablabla'
            if msg:
                break
            print('You need to pass a message!')
        return msg
    except:
        print('Erro: NANIII')


def create(key, value):
    messageId = get_id()
    data = map_pb2.Data(key=int(key), value=value)
    message = map_pb2.Message(id=messageId, command=None, data=data)
    print('sent Create')
    resposta = stub.Create(message)
    printResposta(resposta)


def retrieve(key):
    messageId = get_id()
    data = map_pb2.Data(key=key, value='retrieve')
    message = map_pb2.Message(id=messageId, command=None, data=data)
    print('sent Retrieve')
    resposta = stub.Retrieve(message)
    printResposta(resposta)

def update(key, value):
    messageId = get_id()
    data = map_pb2.Data(key=key, value=value)
    message = map_pb2.Message(id=messageId, command=None, data=data)
    print('sent Update')
    resposta = stub.Update(message)
    printResposta(resposta)


def delete(key):
    messageId = get_id()
    data = map_pb2.Data(key=key, value=None)
    message = map_pb2.Message(id=messageId, command=None, data=data)
    print('sent Delete')
    resposta = stub.Delete(message)
    printResposta(resposta)


def confirmMsg():
    print('Tem certeza que deseja remover esse item? <S/N>')
    resp = input()
    if resp in ['S', 's', 'Sim', 'sim', 'Y', 'y', 'Yes', 'yes']:
        print('Removendo...')
        return True
    else:
        print('Pedido de exclusão cancelado!')
        return False


def printMenu():
    print('\n====================== Lista de Comandos ======================')
    print(' "Escolha um comando para continuar:')
    print(' 1) Create')
    print(' 2) Retrieve')
    print(' 3) Update')
    print(' 4) Delete')
    print(' 0) sair')
    print('================================================================\n')


def printResposta(resposta):
    print('ack: ' + str(resposta.ack))
    print('data: ')
    print('    key: ' + str(resposta.data.key))
    print('    value: ' + str(resposta.data.value))


gRpcByCommandCode = {
    0: stub.Exit,
    1: stub.Create,
    2: stub.Retrieve,
    3: stub.Update,
    4: stub.Delete,
}


threading.Thread(target=speak, args=()).start()
