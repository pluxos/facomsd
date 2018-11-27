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
* Aplicacao cliente. Implementacao do cliente do banco de dados remoto.  *
* O cliente deve obter um ID antes ded se comunicar com o servidor.      *
--------------------------------------------------------------------------
"""
from __future__ import print_function

import grpc
import random
import remotedb_pb2
import remotedb_pb2_grpc


def run():
    # Abrindo arquivo de configuracoes
    arquivo = open('./configs.ini', 'r')
    configs = dict(eval(arquivo.read()))
    arquivo.close()

    servidores = configs['servidores']
    pid = random.randint(0, servidores - 1)
    print('Conectado a: %d' % (pid))

    # Criando um stub e um canal para conectar ao servidor
    channel = grpc.insecure_channel('%s:%d' % configs[pid])
    stub = remotedb_pb2_grpc.RemoteDBStub(channel)

    print(':.:.: Trabalho v 2.0 :.:.:')

    keep_alive = True

    while keep_alive:
        print('Digite um comando ou \\? para obter ajuda!')
        entrada = str(input('> '))
        comando = entrada.split(' ')
        nome = comando[0]

        # Verifica se o comando esta correto e dispara algumas requisicoes.
        if nome.lower() == 'create':
            if len(comando) >= 3:
                chave = int(comando[1])
                valor = comando[2]
                mensagem = stub.create(remotedb_pb2.KVRequest(chave=chave, valor=valor)).mensagem
                print('Servidor: %s' % mensagem)
            else:
                print('Erro: sintaxe correta eh CREATE <chave> <valor>')

        elif nome.lower() == 'read':
            if len(comando) >= 2:
                chave = int(comando[1])
                mensagem = stub.read(remotedb_pb2.KRequest(chave=chave)).mensagem
                print('Servidor: %s' % mensagem)
            else:
                print('Erro: sintaxe correta eh READ <chave>')

        elif nome.lower() == 'update':
            if len(comando) >= 3:
                chave = int(comando[1])
                valor = comando[2]
                mensagem = stub.update(remotedb_pb2.KVRequest(chave=chave, valor=valor)).mensagem
                print('Servidor: %s' % mensagem)
            else:
                print('Erro: sintaxe correta eh UPDATE <chave> <valor>')

        elif nome.lower() == 'delete':
            if len(comando) >= 2:
                chave = int(comando[1])
                mensagem = stub.delete(remotedb_pb2.KRequest(chave=chave)).mensagem
                print('Servidor: %s' % mensagem)
            else:
                print('Erro: sintaxe correta eh DELETE <chave>')

        elif nome.lower() == 'sair':
            keep_alive = False

        elif nome.lower() == '\\?':
            pass

        else:
            print('Erro: comando desconhecido...')


if __name__ == '__main__':
    run()
