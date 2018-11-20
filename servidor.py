# Copyright 2018 - Luis Carlos Silva Junior
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
'''
--------------------------------------------------------------------------
* Servidor da aplicacao. Esse servidor armazena parte do banco de dados  *
* distribuido entre outros servidores.                                   *
--------------------------------------------------------------------------
'''
from concurrent import futures
from multiprocessing.context import Process
from threading import Lock

import grpc
import queue
import random
import remotedb_pb2
import remotedb_pb2_grpc
import sys
import time
import utils


_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class IDGenerator(object):
    '''
    Gera e mantem controle dos identificadores gerados de forma aleatoria
    para o programa.
    '''

    def __init__(self):
        self.gerados = set([])

    def get_id(self):
        '''
        Gera um novo identificador aleatorio,
        '''
        lck = Lock()
        lck.acquire()
        i = random.randint(0, 2**32 - 1)

        while i in self.gerados:
            i = random.randint(0, 2**32 - 1)

        self.gerados.add(i)
        lck.release()

        return i

    def remove_id(self, identificador):
        if identificador in self.gerados:
            self.gerados.remove(identificador)


# Implementacao concreta da interface definida em 'remotedb.proto'. Os
# metodos e suas devidas respostas estao implementados abaixo de acordo
# com a interface.
class RemoteDB(remotedb_pb2_grpc.RemoteDBServicer):

    def __init__(self, requisicoes, inicio, fim, repasse, mensagens, gerador_id):
        self.requisicoes = requisicoes
        self.inicio = inicio
        self.fim = fim
        self.repasse = repasse
        self.mensagens = mensagens
        self.gerador_id = gerador_id

    def create(self, request, context):
        '''
        Cria uma nova entrada no banco de dados remoto.
        '''
        chave = request.chave
        valor = request.valor

        # Criando um identificador unico para a requisicao
        identificador = self.gerador_id.get_id()
        print('AQUI????')

        item = ('create', identificador, chave, valor)

        print(item)
        # Criando uma celula para ser a fila de mensagens de respostas dos
        # clientes.
        self.mensagens.setdefault(identificador, queue.Queue(maxsize=1))

        # Verifica se a chave pertence ao intervalo de chaves do servidor.
        if chave in range(self.inicio, self.fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        # Se nao houver mensagens, a thread atual fica bloqueada
        mensagem = self.mensagens.get(identificador).get()
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def read(self, request, context):
        '''
        Le um registro do banco de dados.
        '''
        chave = request.chave

        # Criando um identificador unico para a requisicao
        identificador = self.gerador_id.get_id()

        item = ('read', identificador, chave)

        # Criando uma celula para ser a fila de mensagens de respostas dos
        # clientes.
        self.mensagens.setdefault(identificador, queue.Queue(maxsize=1))

        # Verifica se a chave pertence ao intervalo de chaves do servidor.
        if chave in range(self.inicio, self.fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        # Se nao houver mensagens, a thread atual fica bloqueada
        mensagem = self.mensagens.get(identificador).get()
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def update(self, request, context):
        '''
        Atualiza um registro no banco.
        '''
        chave = request.chave
        valor = request.valor

        # Criando um identificador unico para a requisicao
        identificador = self.gerador_id.get_id()

        item = ('update', identificador, chave, valor)

        # Criando uma celula para ser a fila de mensagens de respostas dos
        # clientes.
        self.mensagens.setdefault(identificador, queue.Queue(maxsize=1))

        # Verifica se a chave pertence ao intervalo de chaves do servidor.
        if chave in range(self.inicio, self.fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        # Se nao houver mensagens, a thread atual fica bloqueada
        mensagem = self.mensagens.get(identificador).get()
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def delete(self, request, context):
        '''
        Apaga um registro no banco.
        '''
        chave = request.chave

        # Criando um identificador unico para a requisicao
        identificador = self.gerador_id.get_id()

        item = ('delete', identificador, chave)

        # Criando uma celula para ser a fila de mensagens de respostas dos
        # clientes.
        self.mensagens.setdefault(identificador, queue.Queue(maxsize=1))

        # Verifica se a chave pertence ao intervalo de chaves do servidor.
        if chave in range(self.inicio, self.fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        # Se nao houver mensagens, a thread atual fica bloqueada
        mensagem = self.mensagens.get(identificador).get()
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)


def serve(*args):
    pid = args[0]
    inicio = args[1]
    fim = args[2]

    print('Iniciando o processo...')
    print('ID: %d' % (pid))
    print('Faixa: [%d, %d]' % (inicio, fim))

    # Abrindo o arquivo de configuracoes do servidor.
    arquivo = open('.\\configs.ini', 'r')
    configs = dict(eval(arquivo.read()))
    arquivo.close()

    addr, port = configs.get(pid)

    # Criando stubs para o proximo servidor
    if pid == configs.get('servidores') - 1:
        channel_prox = grpc.insecure_channel('%s:%d' % (configs.get(0)))
    else:
        channel_prox = grpc.insecure_channel('%s:%d' % (configs.get(pid + 1)))

    prox_stub = remotedb_pb2_grpc.RemoteDBStub(channel_prox)

    # Criando o banco de dados em memoria inicialmente vazio
    banco = {}

    # Filas para processamento de requisicoes
    requisicoes = queue.Queue() # F1 -> requisicoes
    log = queue.Queue()         # F2 -> guardar no log
    database = queue.Queue()    # F3 -> guardar no banco de dados
    repasse = queue.Queue()     # F4 -> repassar para outros servidores

    # Criando um canal de mensagens para o servidor
    mensagens = {}

    # Instanciando as threads do servidor
    consumidor = utils.ConsumerThread(requisicoes, [log, database])
    db = utils.DatabaseThread(banco, database, mensagens)
    fwd = utils.ForwardThread(prox_stub, repasse, mensagens)

    consumidor.start()
    db.start()
    fwd.start()

    # Instanciando e inicializando o servidor
    servidor = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    remotedb_pb2_grpc.add_RemoteDBServicer_to_server(RemoteDB(requisicoes, inicio, fim, repasse, mensagens, IDGenerator()), servidor)
    servidor.add_insecure_port('%s:%d' % (addr, port))
    servidor.start()

    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        servidor.stop()


if __name__ == '__main__':
    # Inicializando multiplos processos simultaneamente
    launcher = open('.\\launcher', 'r')
    processos = []

    for linha in launcher:
        args = linha.split(' ')

        pid = int(args[0])
        inicio = int(args[1])
        fim = int(args[2])

        # Instanciando os processos dos servidores
        processos.append(Process(target=serve, args=(pid, inicio, fim)))

    # Instanciando diferentes processos servidores
    for p in processos:
        p.start()

    print('Iniciando processos!')

    try:
        while True: pass
    except KeyboardInterrupt:
        print('Finalizando processos!')

        # Matando todos os processos
        for p in processos:
            p.kill()
