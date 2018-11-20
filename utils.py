import threading
import remotedb_pb2


class ConsumerThread(threading.Thread):
    '''
    Consome os elementos de uma fila e transfere uma copia para uma ou mais
    filas destino.
    '''

    def __init__(self, origem, destinos):
        super().__init__()
        self.origem = origem
        self.destinos = destinos

    def run(self):
        while True:
            # Remove o item da fila
            item = self.origem.get()

            # Copia para os demais
            for d in self.destinos:
                d.put(item)


class DatabaseThread(threading.Thread):

    def __init__(self, db, fila, mensagens):
        super().__init__()
        self.db = db
        self.fila = fila
        self.mensagens = mensagens

    def run(self):
        # Processa os elementos da fila de requisicoes e gera as devidas
        # mensagens
        while True:
            item = self.fila.get()

            operacao = item[0]
            identificador = item[1]
            chave = item[2]

            if operacao == 'create':
                # Verifica se o item nao existe no banco de dados
                if not self.db.get(chave):
                    valor = item[3]
                    self.db.setdefault(chave, valor)
                    self.mensagens.get(identificador).put('Registro inserido com sucesso!')
                else:
                    self.mensagens.get(identificador).put('Erro: Ja existe um registro associado a esta chave!')

            elif operacao == 'read':
                # Verifica se o item existe no banco de dados
                if self.db.get(chave):
                    valor = self.db.get(chave, 'null')
                    self.mensagens.get(identificador).put('Registro: %d -> %s' % (chave, valor))

            elif operacao == 'update':
                # Verifica se o item existe no banco
                if self.db.get(chave):
                    valor = item[3]
                    self.db.update({chave: valor})
                    self.mensagens.get(identificador).put('Registro atualizado com sucesso!')
                else:
                    self.mensagens.get(identificador).put('Erro: nao existe registro com essa chave!')                    

            elif operacao == 'delete':
                # Verifica se o item existe no banco
                if self.db.get(chave):
                    del(self.db[chave])
                    self.mensagens.get(identificador).put('Registro apagado com sucesso!')
                else:
                    self.mensagens.get(identificador).put('Erro: nao existe registro com essa chave!')


class ForwardThread(threading.Thread):

    def __init__(self, stub, fila, mensagens):
        super().__init__()
        self.stub = stub
        self.fila = fila
        self.mensagens = mensagens

    def run(self):
        # Repassa as requisicoes para os demais servidores
        while True:
            item = self.fila.get()
            operacao = item[0]
            identificador = item[1]
            chave = item[2]

            if operacao == 'create':
                valor = item[3]
                mensagem = self.stub.create(remotedb_pb2.CreateRequest(chave=chave, valor=valor)).mensagem
            
            elif operacao == 'read':
                mensagem = self.stub.read(remotedb_pb2.ReadRequest(chave=chave)).mensagem

            elif operacao == 'update':
                valor = item[3]
                mensagem = self.stub.update(remotedb_pb2.UpdateRequest(chave=chave, valor=valor)).mensagem

            elif operacao == 'delete':
                mensagem = self.stub.delete(remotedb_pb2.DeleteRequest(chave=chave)).mensagem

            self.mensagens.get(identificador).put(mensagem)
                

