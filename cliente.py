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
* Aplicacao cliente. Implementacao do cliente do banco de dados remoto.  *
* O cliente deve obter um ID antes ded se comunicar com o servidor.      *
--------------------------------------------------------------------------
'''
from __future__ import print_function

import grpc
import random
import remotedb_pb2
import remotedb_pb2_grpc


def run():
    # Abrindo o arquivo de configuracoes
    arquivo = open('.\\configs.ini', 'r')
    configs = dict(eval(arquivo.read()))
    arquivo.close()

    servidores = configs.get('servidores')
    pid = random.randint(0, servidores - 1)
    print('Conectado a %d!' % (pid))

    addr, port = configs.get(pid)

    # Criando um stub e um channel para o servidor de banco de dados
    # remoto.
    db_channel = grpc.insecure_channel('%s:%d' % (addr, port))
    db_stub = remotedb_pb2_grpc.RemoteDBStub(db_channel)

    print(':.:.: Banco de dados remoto V2.0 :.:.:')

    keep_alive = True

    while keep_alive:
        print('Digite um comando ou \\? para obter ajuda')
        entrada = str(input('> '))
        comando = entrada.split(' ')
        operacao = comando[0]

        if operacao.lower() == 'create':
            chave = int(comando[1])
            valor = comando[2]
            mensagem = db_stub.Create(remotedb_pb2.CreateRequest(chave=chave, valor=valor)).mensagem
            print(mensagem)

        elif operacao.lower() == 'read':
            chave = int(comando[1])
            mensagem = db_stub.Read(remotedb_pb2.ReadRequest(chave=chave)).mensagem
            print(mensagem)

        elif operacao.lower() == 'update':
            chave = int(comando[1])
            valor = comando[2]
            mensagem = db_stub.Update(remotedb_pb2.UpdateRequest(chave=chave, valor=valor)).mensagem
            print(mensagem)

        elif operacao.lower() == 'delete':
            chave = int(comando[1])
            mensagem = db_stub.Delete(remotedb_pb2.DeleteRequest(chave=chave)).mensagem
            print(mensagem)

        elif operacao.lower() == 'sair':
            print('Saindo...')
            keep_alive = False
        
        else:
            print('Comando invalido...')


if __name__ == '__main__':
    run()