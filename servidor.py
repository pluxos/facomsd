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
"""
--------------------------------------------------------------------------
* Servidor da aplicacao. Esse servidor armazena parte do banco de dados  *
* distribuido entre outros servidores.                                   *
--------------------------------------------------------------------------
"""
from concurrent import futures
from multiprocessing.context import Process

import grpc
import os
import queue
import random
import remotedb_pb2
import remotedb_pb2_grpc
import threading
import time
import utils


_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class IDGenerator(object):
    """
    Gera ID's de forma automatica para ser usado na troca de mensagens
    entre threads.
    """


    def __init__(self):
        self.identificadores = set()


    def get_id(self):
        """
        Gera um identificador unico para identificar uma mensagem de forma
        unica no canal de mensagens.
        """
        lock = threading.Lock()
        lock.acquire()
        
        identificador = random.randint(0, 2**32 - 1)

        while identificador in self.identificadores:
            identificador = random.randint(0, 2**32 - 1)

        self.identificadores.add(identificador)

        lock.release()


    def remove_id(self, identificador):
        """
        Remove um identificador do conjunto de identificadores do objeto.
        Se o Identificador nao existe, nada acontece.
        @param identificador: um identificador previamente gerado.
        """
        lock = threading.Lock()
        lock.acquire()

        if identificador in self.identificadores:
            self.identificadores.remove(identificador)

        lock.release()


_ID_GENERATOR = IDGenerator()


class RemoteDB(remotedb_pb2_grpc.RemoteDBServicer):
    """
    Implementacao concreta da interface definida em remotedb.proto.
    """


    def __init__(self, requisicoes, repasse, intervalo, mensagens):
        super().__init__()
        self.requisicoes = requisicoes
        self.repasse = repasse
        self.intervalo = intervalo
        self.mensagens = mensagens


    def Create(self, request, context):
        """
        Cria uma nova entrada no banco de dados.
        """
        chave = request.chave
        valor = request.valor

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        self.mensagens.setdefault(identificador, queue.Queue())

        item = (identificador, 'create', chave, valor)

        # Verifica se a chave pertence ao intervalo do servidor
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens.get(identificador).get()

        _ID_GENERATOR.remove_id(identificador)
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)


    def Read(self, request, context):
        """
        Le um registro no banco de dados.
        """
        chave = request.chave

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        self.mensagens[identificador] = queue.Queue()

        item = (identificador, '', chave)

        # Verifica se a chave pertence ao intervalo do servidor
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()

        _ID_GENERATOR.remove_id(identificador)
        del(mensagem[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)


    def Update(self, request, context):
        """
        Atualiza um registro no banco de dados.
        """
        chave = request.chave
        valor = request.valor

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        self.mensagens.setdefault(identificador, queue.Queue())

        item = (identificador, 'update', chave, valor)

        # Verifica se a chave pertence ao intervalo do servidor
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens.get(identificador).get()

        _ID_GENERATOR.remove_id(identificador)
        del(self.mensagens[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)


    def Delete(self, request, context):
        """
        Apaga um registro do banco de dados.
        """
        chave = request.chave

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        self.mensagens[identificador] = queue.Queue()

        item = (identificador, '', chave)

        # Verifica se a chave pertence ao intervalo do servidor
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()

        _ID_GENERATOR.remove_id(identificador)
        del(mensagem[identificador])

        return remotedb_pb2.ServerReply(mensagem=mensagem)


def recupera_banco(caminho_log, caminho_snaps):
    """
    Recupera o banco de dados a partir dos ultimos logs e snapshots
    mantidos em disco.
    """
    banco = {}

    # Listando os arquivos nas pastas de log e de snapthots
    logs = os.listdir(caminho_log)
    snaps = os.listdir(caminho_snaps)
    i = len(snaps) - 1

    if len(logs) == len(snaps) and len(logs) > 0:
        # Abrindo os arquivos de log
        for arquivo in reversed(snaps):
            try:
                snap = open('%s/%s' % (caminho_snaps, arquivo), 'r')
                banco = dict(eval(snap.read()))
                snap.close()
                break
            except Exception:
                i -= 1

        for arquivo in logs[i:]:
            try:
                log = open('%s/%s' % (caminho_snaps, arquivo), 'r')
                
                # Aplicando as alteracoes conforme determinado pelo
                # arquivo de log.
                for linha in log:
                    comando = linha.split(' ')
                    nome = linha[0]

                    if nome == 'create':
                        chave = int(comando[1])
                        valor = comando[2]

                        if not banco.get(chave):
                            banco.setdefault(chave, valor)

                    elif nome == 'update':
                        chave = int(comando[1])
                        valor = comando[2]

                        if banco.get(chave):
                            banco.update({chave: valor})

                    elif nome == 'delete':
                        chave = int(comando[1])

                        if banco.get(chave):
                            del(banco[chave])

                log.close()
            except Exception:
                pass

    return banco


def serve(*args):
    # Parametros de inicializacao do processo servidor
    pid = int(args[0])
    inicio = int(args[1])
    fim = int(args[2])

    # Arquivo de configuracao do servidor
    arquivo = open('./configs.ini', 'r')
    configs = dict(eval(arquivo.read()))
    arquivo.close()

    # Criando um stub para trocar mensagens com o servidor vizinho
    channel_prox = grpc.insecure_channel('%s:%d' % (configs.get((pid + 1) % configs.get('servidores'))))
    stub_prox = remotedb_pb2_grpc.RemoteDBStub(channel_prox)

    # Carregando o banco de dados em memoria ou criando ele inicialmente
    # vazio
    banco = recupera_banco('./log/%d' % (pid), './snapshots/%d' % (pid))
  
    # Esvaziando as pastas de logs e de snapshots
    for arquivo in os.listdir('./log/%d' % (pid)):
        os.remove('./log/%d/%s' % (pid, arquivo))

    for arquivo in os.listdir('./snapshots/%d' % (pid)):
        os.remove('./snapshots/%d/%s' % (pid, arquivo))

    # Realizando o snapshot inicial e gravando o primeiro arquivo de log
    snap = open('./snapshots/%d/snap.0' % (pid), 'w')
    snap.write(str(banco) + '\n')
    snap.close()
    log = open('./log/%d/log.0' % (pid), 'w')
    log.write('\n')
    log.close()

    # Outros recursos alocados usados nas threads
    versao = 0
    log_fd = open('./log/%d/log.%d' % (pid, versao), 'w')

    # Filas para processamento de requisicao
    fila_requisicoes = queue.Queue()  # F1
    fila_log = queue.Queue()          # F2
    fila_banco = queue.Queue()        # F3
    fila_repasse = queue.Queue()      # F4

    # Canal de troca de mensagens entre threads do servidor
    mensagens = {}

    # Instanciando threads para processar os comandos das filas
    consumidor = utils.Consumidor(fila_requisicoes, (fila_log, fila_banco))
    logger = utils.Logger(fila_log, log_fd)
    processador = utils.Processador(fila_banco, banco, mensagens)
    repasse = utils.Repasse(fila_repasse, stub_prox, mensagens)

    # Iniciando as threads
    consumidor.start()
    logger.start()
    processador.start()
    repasse.start()

    # Carregando o servidor
    servidor = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    remotedb_pb2_grpc.add_RemoteDBServicer_to_server(RemoteDB(fila_requisicoes, fila_repasse, (inicio, fim), mensagens), servidor)
    servidor.add_insecure_port('%s:%d' % (configs.get(pid)))
    servidor.start()

    try:
        while True:
            # Incrementa a versao do novo snapshot e log
            versao += 1
            versao_antiga = versao - 3

            logger.pause()
            processador.pause()
            
            # time.sleep(_ONE_DAY_IN_SECONDS)
            time.sleep(5)
            snap = open('./snapshots/%d/snap.%d' % (pid, versao), 'w')
            snap.write(str(banco) + '\n')
            snap.close()

            log_antigo = log_fd
            log_fd = open('./log/%d/log.%d' % (pid, versao), 'w')
            log_antigo.close()

            # Excluindo as versoes mais antigas
            if versao_antiga >= 0:
                os.remove('./log/%d/log.%d' % (pid, versao_antiga))
                os.remove('./snapshots/%d/snap.%d' % (pid, versao_antiga))

            time.sleep(2)
            processador.cont()
            logger.cont()
    except KeyboardInterrupt:
        print('Matando o servidor %d...' % (pid))
        servidor.stop(0)



if __name__ == '__main__':
    launcher = open('./launcher', 'r')
    processos = []

    for linha in launcher:
        processos.append(Process(target=serve, args=linha.split(' ')))

    for p in processos:
        p.start()

    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        print('Finalizando processos...')

        for p in processos:
            p.kill()

    launcher.close()
