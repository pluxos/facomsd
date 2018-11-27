import remotedb_pb2
import threading


class Consumidor(threading.Thread):
    """
    Consome itens de uma fila de origem e transfere para as demais filas
    de destino.
    """


    def __init__(self, origem, destinos):
        super().__init__(name='Consumidor')
        self.origem = origem
        self.destinos = destinos


    def run(self):
        while True:
            item = self.origem.get()

            for d in self.destinos:
                d.put(item)


class Logger(threading.Thread):
    """
    Consome os comandos da fila de requisicoes e grava o que foi feito
    num arquivo de log.
    """


    def __init__(self, fila, fd):
        super().__init__(name='Logger')
        self.fila = fila
        self.fd = fd
        self._pause = False


    def cont(self):
        lock = threading.Lock()
        lock.acquire()
        self._pause = False
        lock.release()


    def pause(self):
        lock = threading.Lock()
        lock.acquire()
        self._pause = True
        lock.release()


    def run(self):
        while True:
            # "Pausa" a Thread enquanto nao tem nada para gravar
            while self._pause: pass

            # Codigo da thread
            item = self.fila.get()
            nome = item[1]
            chave = int(item[2])

            if nome == 'create' or nome == 'update':
                valor = item[3]
                self.fd.write('%s %d %s\n' % (nome, chave, valor))
            
            elif nome == 'delete':
                self.fd.write('%s %d\n' % (nome, chave))

            self.fd.flush()


class Processador(threading.Thread):
    """
    Processa comandos em uma fila de requisicoes e realiza alteracoes no
    banco de dados em memoria.
    """


    def __init__(self, fila, banco, mensagens):
        super().__init__(name='Processador')
        self.fila = fila
        self.banco = banco
        self.mensagens = mensagens
        self._pause = False


    def cont(self):
        lock = threading.Lock()
        lock.acquire()
        self._pause = False
        lock.release()


    def pause(self):
        """
        Sinaliza para a thread que ela deve interromper sua execucao
        temporariamente.
        """
        lock = threading.Lock()
        lock.acquire()
        self._pause = True
        lock.release()


    def run(self):
        while True:
            # Interrompe a thread aqui se ela estiver pausada
            while self._pause: pass

            # Codigo da thread
            item = self.fila.get()
            identificador = item[0]
            nome = item[1]
            chave = int(item[2])

            # Verifica o nome do comando e realiza as devidas alteracoes
            # (ou nao) no banco.
            if nome == 'create':
                valor = item[3]

                if not self.banco.get(chave):
                    self.banco.setdefault(chave, valor)
                    mensagem = 'Registro inserido com sucesso!'
                else:
                    mensagem = 'Ja existe um registro com essa chave!'

            elif nome == 'read':
                valor = self.banco.get(chave)

                if valor:
                    mensagem = 'Registro: (%d, "%s")' % (chave, valor)
                else:
                    mensagem = 'Registro: (%d, NULL)' % (chave, valor)

            elif nome == 'update':
                valor = item[3]

                if self.banco.get(chave):
                    self.banco.update({chave: valor})
                    mensagem = 'Registro atualizado com sucesso!'
                else:
                    mensagem = 'Nao existe registro com essa chave!'

            elif nome == 'delete':
                if self.banco.get(chave):
                    del(self.banco[chave])
                    mensagem = 'Registro apagado com sucesso!'
                else:
                    mensagem = 'Nao existe registro com essa chave!'

            self.mensagens.get(identificador).put(mensagem)


class Repasse(threading.Thread):
    """
    Delega para outro servidor a tarefa de modificar o banco de dados.
    """


    def __init__(self, fila, stub, mensagens):
        super().__init__(name='Repasse')
        self.fila = fila
        self.stub = stub
        self.mensagens = mensagens


    def run(self):
        while True:
            item = self.fila.get()
            identificador = item[0]
            nome = item[1]
            chave = int(item[2])

            if nome == 'create':
                valor = item[3]
                mensagem = self.stub.Create(remotedb_pb2.CreateRequest(chave=chave, valor=valor)).mensagem

            elif nome == 'read':
                mensagem = self.stub.Read(remotedb_pb2.ReadRequest(chave=chave)).mensagem

            elif nome == 'update':
                valor = item[3]
                mensagem = self.stub.Update(remotedb_pb2.UpdateRequest(chave=chave, valor=valor)).mensagem

            elif nome == 'delete':
                mensagem = self.stub.Delete(remotedb_pb2.DeleteRequest(chave=chave)).mensagem

            self.mensagens[identificador].put(mensagem)
