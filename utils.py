import os
import threading


class Consumidor(threading.Thread):
    """
    Consome itens armazenados em uma fila associado a uma Thread e copia
    os itens para uma ou mais filas de destino.
    """

    def __init__(self, queue, targets, thread_name="consumidor"):
        super().__init__(name=thread_name) # nome da thread
        self.queue = queue                 # fila associada a thread
        self.targets = targets             # filas destino

    def run(self):
        """
        Copia os itens de uma fila de origem para uma ou mais filas de
        destino.
        """
        while True:
            # retira um item de uma fila e copia para as temais filas
            # associadas a esta thread
            item = self.queue.get()

            for tg in self.targets:
                tg.put(item)


class Logger(threading.Thread):
    """
    Grava as operacoes realizadas por um ou mais usuarios em um arquivo
    de log para fins de recuperacao de falhas.
    """        

    def __init__(self, queue, log_fd, thread_name="logger"):
        super().__init__(name=thread_name)
        self.queue = queue
        self.log_fd = log_fd

    def run(self):
        """
        Retira as requisicoes da fila de requests e as grava em disco
        """
        while True:
            item = self.queue.get()
            self.log_fd.write(item[0] + "\n")
            self.log_fd.flush()
            os.fsync(self.log_fd.fileno)


class Processamento(threading.Thread):
    """
    Processa as requisicoes armazenadas na fila de requisicoes e depois
    gera as devidas respostas ou alteracoes no banco de dados da
    aplicacao.
    """

    def __init__(self, queue, db, thread_name="processamento"):
        super().__init__(name=thread_name)
        self.queue = queue
        self.db = db

    def run(self):
        """
        Processa as requisicoes na fila de requisicoes na fila do
        servidor
        """
        while True:
            # retira um item da fila de requisicoes. Um item esta no formato
            # (tokens, socket)
            toks, conn = self.queue.get()
            comando = toks[0]

            if comando.lower() == "create":
                chave = int(toks[1])
                valor = toks[2]

                # a chave ja existe no registro?
                if self.db.get(chave) is not None:
                    resposta = "ERRO: A chave {0} ja existe no banco de dados!".format(chave)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
                else:
                    self.db.setdefault(chave, valor)
                    resposta = "MENSAGEM: Registro {0}->{1} criado com exito!".format(chave, valor)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
            
            elif comando.lower() == "read":
                chave = int(toks[1])
                valor = self.db.get(chave)

                # checa se o valor retornado eh "null"
                if valor is None:
                    resposta = "ERRO: Nao existe registro associado a chave {0}!".format(chave)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
                else:
                    resposta = "MENSAGEM: Registro {0}->{1}!".format(chave, valor)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))

            elif comando.lower() == "update":
                chave = int(toks[1])
                valor = toks[2]

                # se o valor retornado for "nulo" entao nao existe nada para atualizar
                if self.db.get(chave) is None:
                    resposta = "ERRO: Nao existe registro associado a chave {0}!".format(chave)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
                else:
                    self.db.update({chave: valor})
                    resposta = "MENSAGEM: Registro {0}->{1} atualizado!".format(chave, valor)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))

            elif comando.lower() == "delete":
                chave = int(toks[1])
                valor = self.db.get(chave)

                # se o valor for "null" entao nao existe nada para apagar
                if valor is None:
                    resposta = "ERRO: Nao existe registro associado a chave {0}!".format(chave)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
                else:
                    del(self.db[chave])
                    resposta = "MENSAGEM: Registro {0}->{1} apagado!".format(chave, valor)
                    # retorna a mensagem para o devido socket associado a requisicao
                    conn.send(bytes(resposta, "utf-8"))
            elif comando.lower() == "exit":
                conn.send(bytes("EXIT", "utf-8"))
                conn.close()


class Requisicoes(threading.Thread):

    def __init__(self, client_sock, addr, queue, thread_name="requisicoes"):
        super().__init__(name=thread_name, daemon=True)
        self.client_sock = client_sock
        self.addr = addr
        self.queue = queue

    def run(self):
        """
        Processa as requisicoes de um usuario conectado ao servidor enquanto
        ele nao der o sinal para finalizar a thread. As mensagens sao
        verificadas no lado do cliente, portanto todas mensagens processadas
        aqui estao corretas.
        """
        keep_alive = True

        while keep_alive:
            # recebe uma mensagem do usuario
            mensagem = self.client_sock.recv(4096).decode("utf-8")
            # print(mensagem)
            # quebra a mensagem do cliente em tokens
            toks = mensagem.split(" ")

            # se o cliente deseja sair, entao a thread deve morrer
            if toks[0].lower() == "exit":
                keep_alive = False
            # armazenando a nova requisicao na fila de requisicoes
            item = toks, self.client_sock
            self.queue.put(item)
