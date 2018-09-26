import threading


class Consumidor(threading.Thread):
    """
    Essa thread consome tudo que esta em determinadas filas e depois
    copia o item consumido para demais filas chamadas target.
    """

    def __init__(self, queue, targets):
        super().__init__(name="consumidor")
        self.queue = queue
        self.targets = targets

    def run(self):
        """
        Copia as filas de uma fila queue para outras target
        """
        while True:
            # retirando os elementos da fila queue e copiando para as
            # demais filas em targets
            item = self.queue.get()

            for tg in self.targets:
                tg.put(item)


class Processamento(threading.Thread):
    """
    Processa as mensagens enviada por cada usuario conecado ao sistema e
    gera as devidas respostas para cada cliente.
    """
    
    def __init__(self, connection_socket, addr, queue, db):
        super().__init__(name="processamento")
        self.connection_socket = connection_socket
        self.addr = addr
        self.queue = queue
        self.db = db

    def run(self):
        """
        O cliente atual mantem esta thread viva ate que o comando EXIT seja
        digitado no console de aplicacao. Entao a thread e a conexao sao
        encerrados.
        """
        while True:
            # obtendo os comandos da fila de execucao do sistema
            item = self.queue.get()

            comando = item[0]

            # verificando qual foi o comando enviado pelo usuario
            # ( CREATE | READ | UPDATE | DELETE )
            if comando.lower() == "create":
                chave, valor = item[1], item[2]

                # checa se a chave ja existe no banco de dados
                if self.db.get(chave) is not None:
                    resposta = "ERRO: A chave {0} ja existe no banco de dados!".format(chave)
                    self.connection_socket.send(bytes(resposta, "utf-8"))
                # senao insere a chave e o valor
                else:
                    self.db.setdefault(chave, valor)
                    resposta = "MENSAGEM: Novo registro criado: {0} -> {1}".format(chave, valor)
                    self.connection_socket.send(bytes(resposta, "utf-8"))
            # encerra a thread e fecha todas as conexoes com o cliente
            elif comando.lower() == "exit":
                self.connection_socket.close()
                # encerrando o laco e consequentemente a thread
                break


class Requisicoes(threading.Thread):
    """
    Recebe uma conexao com o usuario e trata as mensagens recebidas pelo
    usuario e depois as insere na fila de requisicoes. A corretude dos
    comandos eh de responsabilidade da aplicacao cliente.
    """

    def __init__(self, connection_socket, addr, queue):
        super().__init__(name="requisicoes")
        self.connection_socket = connection_socket
        self.addr = addr
        self.queue = queue

    def run(self):
        """
        Para cada novo usuario conectado, uma nova thread sera criada
        para tratar os comandos recebidos.
        """
        # tratando uma mensagem enviada pelo cliente. A verificacao da
        # corretude dos comandos eh feita no lado do cliente
        mensagem = self.connection_socket.recv(1024).decode("utf-8")

        # quebrando a mensagem em tokens
        toks = mensagem.split(" ")
        # item = [self.addr] + toks
        item = toks

        # guardando a requisicao na fila de requisicoes
        self.queue.put(item)
