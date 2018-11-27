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


_ONE_DAY_IN_SECONDS = 60 * 60 * 24
_ID_LOCK = threading.Lock()


class IDGenerator(object):
    """
    Gerador automatico de ID's para identificacao de mensagens entre
    threads no servidor.
    """

    def __init__(self):
        self.identificadores = set()

    def get_id(self):
        """
        Gera um novo identificador unico e salva este para nao ser gerado
        novamente.
        """
        _ID_LOCK.acquire()
        identificador = random.randint(0, 2**32 - 1)

        while identificador in self.identificadores:
            identificador = random.randint(0, 2**32 - 1)

        self.identificadores.add(identificador)
        _ID_LOCK.release()

        return identificador

    def remove_id(self, identificador):
        """
        Remove um identificador e indica que ele ja nao esta mais sendo
        usado. Se ele nao foi gerado, nada acontece.
        """
        _ID_LOCK.acquire()
        if identificador in self.identificadores:
            self.identificadores.remove(identificador)
        _ID_LOCK.release()


_ID_GENERATOR = IDGenerator()


class RemoteDB(remotedb_pb2_grpc.RemoteDBServicer):
    """
    Implementacao concreta da interface grpc definida no arquivo em
    "./protos/remotedb.proto".
    """

    def __init__(self, requisicoes, repasse, intervalo, mensagens):
        super().__init__()
        self.requisicoes = requisicoes
        self.repasse = repasse
        self.intervalo = intervalo
        self.mensagens = mensagens

    def create(self, request, context):
        """
        Cria uma nova entrada no banco de dados distribuidos.
        """
        chave = request.chave
        valor = request.valor

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        item = (identificador, 'create', chave, valor)
        self.mensagens[identificador] = queue.Queue()

        # A chave pertence ao intervalo de chaves do servidor?
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()
        del(self.mensagens[identificador])

        _ID_GENERATOR.remove_id(identificador)

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def read(self, request, context):
        """
        Le um registro no banco de dados do servidor.
        """
        chave = request.chave

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        item = (identificador, 'read', chave, None)
        self.mensagens[identificador] = queue.Queue()

        # A chave pertence ao intervalo do servidor?
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()
        del(self.mensagens[identificador])

        _ID_GENERATOR.remove_id(identificador)

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def update(self, request, context):
        """
        Atualiza uma nova entrada no banco de dados distribuidos.
        """
        chave = request.chave
        valor = request.valor

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        item = (identificador, 'update', chave, valor)
        self.mensagens[identificador] = queue.Queue()

        # A chave pertence ao intervalo de chaves do servidor?
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()
        del(self.mensagens[identificador])

        _ID_GENERATOR.remove_id(identificador)

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def delete(self, request, context):
        """
        Apaga um registro no banco de dados do servidor.
        """
        chave = request.chave

        identificador = _ID_GENERATOR.get_id()
        inicio, fim = self.intervalo

        item = (identificador, 'delete', chave, None)
        self.mensagens[identificador] = queue.Queue()

        # A chave pertence ao intervalo do servidor?
        if chave in range(inicio, fim + 1):
            self.requisicoes.put(item)
        else:
            self.repasse.put(item)

        mensagem = self.mensagens[identificador].get()
        del(self.mensagens[identificador])

        _ID_GENERATOR.remove_id(identificador)

        return remotedb_pb2.ServerReply(mensagem=mensagem)

    def get_range(self, request, context):
        """
        Obtem o intervalo de valores de um determinado servidor.
        """
        
        return remotedb_pb2.ServerReply(mensagem='(%d, %d)' % tuple(self.intervalo))


def recupera_banco(caminho_logs, caminho_snaps):
    """
    Busca no caminho do log e no caminho dos snapshots por informacoes de
    recuperacao do banco de dados.
    @param caminho_logs: diretorio onde estao armazenados os logs deste
    servidor.
    @param caminho_snaps: diretorio onde estao armazenados os snapshots
    deste servidor.
    @return: o banco de dados anterior ou um banco novo.
    """
    logs = os.listdir(caminho_logs)
    snaps = os.listdir(caminho_snaps)
    banco = {}
    i = len(snaps) - 1

    # Tenta recuperar a partir da versao mais nova dos snapshots
    for arquivo in reversed(snaps):
        try:
            snap = open('%s/%s' % (caminho_snaps, arquivo), 'r')
            banco = dict(eval(snap.read()))
            snap.close()
            break
        except IOError:
            i = i - 1

    # A partir do ultimo log valido, tentar aplicar alguma operacao nao
    # aplicada
    for arquivo in logs[i:]:
        try:
            log = open('%s/%s' % (caminho_logs, arquivo), 'r')

            # Aplicando operacoes no banco de dados
            for linha in log:
                comando = linha.split(' ')
                nome = comando[0]
                chave = int(comando[1])

                if nome == 'create':
                    valor = comando[2]

                    if not banco.get(chave):
                        banco[chave] = valor

                elif nome == 'update':
                    valor = comando[2]

                    if banco.get(chave):
                        banco[chave] = valor

                elif nome == 'delete':
                    if banco.get(chave):
                        del(banco[chave])

            log.close()
        except IOError:
            pass

    return banco


def run(*args):
    pid = int(args[0])
    inicio = int(args[1])
    fim = int(args[2])

    # Exibindo informacoes do servidor (teste)
    print('Servidor: ID=%d intervalo=[%d, %d]' % (pid, inicio, fim))

    # Arquivo de configuracao do servidor:
    arquivo = open('./configs.ini', 'r')
    configs = dict(eval(arquivo.read()))
    arquivo.close()


    # Criando stubs para os servidores vizinhos: anterior e o proximo
    if pid - 1 < 0:
        pid_ant = configs['servidores'] - 1
    else:
        pid_ant = pid - 1

    if pid + 1 == configs['servidores']:
        pid_prox = 0
    else:
        pid_prox = pid + 1

    channel_ant = grpc.insecure_channel('%s:%d' % configs[pid_ant])
    stub_ant = remotedb_pb2_grpc.RemoteDBStub(channel_ant)
    channel_prox = grpc.insecure_channel('%s:%d' % configs[pid_prox])
    stub_prox = remotedb_pb2_grpc.RemoteDBStub(channel_prox)

    # Banco de dados em memoria:
    banco = recupera_banco('./lgs/%d' % (pid), './snaps/%d' % (pid))
    print('%d=%s' % (pid, str(banco)))

    log_path = './lgs/%d' % (pid)
    snp_path = './snaps/%d' % (pid)

    for arquivo in os.listdir(log_path):
        os.remove('%s/%s' % (log_path, arquivo))

    for arquivo in os.listdir(snp_path):
        os.remove('%s/%s' % (snp_path, arquivo))

    # Filas de requisicoes usadas no servidor
    fila_requisicoes = queue.Queue() # F1
    fila_log = queue.Queue()         # F2
    fila_banco = queue.Queue()       # F3
    fila_repasse = queue.Queue()     # F4

    # "Fila" de mensagens do servidor.
    mensagens = {}

    # Recursos compartilhados entre o programa principal e as threads:
    # ---------------------- SHARED RESOURCES ----------------------------
    # --- BANCO ---
    lock_banco = threading.Lock()
    snap = open('./snaps/%d/0.snap' % pid, 'w')
    snap.write(str(banco))
    snap.close()
    # -------------
    # --- LOG ---
    versao = 0
    log_fd = open('./lgs/%d/%d.log' % (pid, versao), 'w')
    lock_log = threading.Lock()
    # -----------
    # --------------------------------------------------------------------

    # Funcoes aninhadas para terem acesso a recursos globais do servidor.
    # Cada funcao tem acesso aos mesmos locks, ao banco e ao arquivo de
    # log.
    # --------------------------- TARGETS --------------------------------
    def consumidor():
        """
        Target para thread consumidora
        """
        while True:
            # Copiando os itens da fila de requisicoes para a fila do
            # banco e a fila do log.
            item = fila_requisicoes.get()

            fila_log.put(item)
            fila_banco.put(item)

    def logger():
        """
        Consome os itens de uma de log e escreve num arquivo de log as
        operacoes executadas em cima do estado atual do banco.
        """
        while True:
            item = fila_log.get()
            identificador, nome, chave, valor = item

            lock_log.acquire()

            if nome == 'create' or nome == 'update':
                log_fd.write('%s %d %s\n' % (nome, chave, valor))

            elif nome == 'delete':
                log_fd.write('%s %d\n' % (nome, chave))

            log_fd.flush()
            lock_log.release()

    def processa_comandos():
        """
        Target para a thread que processa requisicoes
        """
        while True:
            # Retira itens da fila do banco e os processa, realizando as
            # devidas alteracoes no banco de dados.
            item = fila_banco.get()
            identificador, nome, chave, valor = item

            lock_banco.acquire()
            print('processa', pid)

            if nome == 'create':
                if not banco.get(chave):
                    banco[chave] = valor
                    mensagem = 'Registro criado com sucesso!'
                else:
                    mensagem = 'Ja existe um registro com essa chave'

            elif nome == 'read':
                valor = banco.get(chave)

                if valor:
                    mensagem = 'Registro: %d -> "%s"' % (chave, valor)
                else:
                    mensagem = 'Registro: %d -> null' % (chave)

            elif nome == 'update':
                if banco.get(chave):
                    banco[chave] = valor
                    mensagem = 'Registro atualizado com sucesso!'
                else:
                    mensagem = 'Nao existe registro com essa chave!'

            elif nome == 'delete':
                if banco.get(chave):
                    del(banco[chave])
                    mensagem = 'Registro apagado com sucesso!'
                else:
                    mensagem = 'Nao existe registro com essa chave!'
            
            lock_banco.release()

            mensagens[identificador].put(mensagem)

    def repasse():
        while True:
            item = fila_repasse.get()
            identificador, nome, chave, valor = item

            # Selecionando o stub mais proximo do servidor atual para enviar a requisicao
            intervalo_a = eval(stub_ant.get_range(remotedb_pb2.Request()).mensagem)
            intervalo_p = eval(stub_prox.get_range(remotedb_pb2.Request()).mensagem)

            print('repasse', pid)

            distancia_a = abs(chave - max(intervalo_a))
            distancia_p = abs(chave - max(intervalo_p))

            if distancia_a <= distancia_p:
                # print('ant')
                stub = stub_ant
            else:
                # print('prox')
                stub = stub_prox

            if nome == 'create':
                mensagem = stub.create(remotedb_pb2.KVRequest(chave=chave, valor=valor)).mensagem
            
            elif nome == 'read':
                mensagem = stub.read(remotedb_pb2.KRequest(chave=chave)).mensagem

            elif nome == 'update':
                mensagem = stub.update(remotedb_pb2.KVRequest(chave=chave, valor=valor)).mensagem

            elif nome == 'delete':
                mensagem = stub.update(remotedb_pb2.KRequest(chave=chave)).mensagem

            mensagens[identificador].put(mensagem)
    # --------------------------------------------------------------------

    # Instanciando as threads responsaveis pela execucao dos targets. Cada
    # thread devera controlar os recursos que acessa.
    # --------------------------- THREADS --------------------------------
    threading.Thread(target=consumidor, name='consumidor').start()
    threading.Thread(target=logger, name='logger').start()
    threading.Thread(target=processa_comandos, name='processador').start()
    threading.Thread(target=repasse, name='repasse').start()
    # --------------------------------------------------------------------

    # Iniciando o servidor
    servidor = grpc.server(futures.ThreadPoolExecutor(max_workers=20))
    remotedb_pb2_grpc.add_RemoteDBServicer_to_server(RemoteDB(fila_requisicoes, fila_repasse, (inicio, fim), mensagens), servidor)
    servidor.add_insecure_port('%s:%d' % (configs[pid]))
    servidor.start()

    try:
        while True:
            # time.sleep(_ONE_DAY_IN_SECONDS)
            time.sleep(5)
            # Passado os U segundos, fazer um snapshot e outro log
            lock_log.acquire()

            # Mudando o arquivo de log e apagando os mais antigos
            versao += 1
            versao_antiga = versao - 3
            snap = open('./snaps/%d/%d.snap' % (pid, versao), 'w')
            snap.write(str(banco) + '\n')
            snap.close()

            log_fd_anterior = log_fd
            log_fd = open('./lgs/%d/%d.log' % (pid, versao), 'w')
            log_fd_anterior.close()

            if versao_antiga >= 0:
                os.remove('./lgs/%d/%d.log' % (pid, versao_antiga))
                os.remove('./snaps/%d/%d.snap' % (pid, versao_antiga))

            lock_log.release()
    except KeyboardInterrupt:
        print('Finalizando o servidor: %d' % (pid))


if __name__ == '__main__':
    launcher = open('./launcher', 'r')
    processos = []

    for linha in launcher:
        processos.append(Process(target=run, args=linha.split(' ')))

    for p in processos:
        p.start()

    # Aguardando uma interrupcao do teclado para finalizar os processos
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        print('Finalizando processos...')

        for p in processos:
            p.kill()

    launcher.close()
