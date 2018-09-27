import socket
import threading
import time


class Receiver(threading.Thread):
    """
    Recebe mensagens do servidor e depois exibe a mensagem para o usuario
    """

    def __init__(self, conn_socket, thread_name="receiver"):
        super().__init__(name=thread_name)
        self.conn_socket = conn_socket

    def run(self):
        """
        Recebe as mensagens do servidor e depois exibe para o usuario.
        """
        keep_alive = True

        while keep_alive:
            resposta = self.conn_socket.recv(4096).decode("utf-8")
            print(resposta)

            if resposta.lower() == "exit":
                keep_alive = False


arquivo = open("C:\\Users\\Luis Carlos\\Documents\\GitHub\\sd\\facomsd\\configs.ini", "r")
configs = eval(arquivo.read())
arquivo.close()

host = socket.gethostname()
port = configs.get("port", 12345)

cli_socket = socket.socket()
cli_socket.connect((host, port))

# instanciando as duas threads do cliente
receiver = Receiver(cli_socket)

# inicializando as threads do cliente
receiver.start()

keep_alive = True

# a thread principal sera o sender
while keep_alive:
    print("Digite um comando ( CREATE | READ | UPDATE | DELETE | EXIT ):")
    sentence = input("> ")
    toks = sentence.split(" ")

    if toks[0].lower() == "create":
        if len(toks) == 3 and toks[1].isdigit():
            cli_socket.send(bytes(sentence, "utf-8"))
        else:
            print("ERRO: Sintaxe incorreta -> CREATE chave valor")
            print("chave: INTEGER")
            print("valor: STRING")
    elif toks[0].lower() == "read":
        if len(toks) == 2 and toks[1].isdigit():
            cli_socket.send(bytes(sentence, "utf-8"))
        else:
            print("ERRO: Sintaxe incorreta -> READ chave")
            print("chave: INTEGER")
    elif toks[0].lower() == "update":
        if len(toks) == 3 and toks[1].isdigit():
            cli_socket.send(bytes(sentence, "utf-8"))
        else:
            print("ERRO: sintaxe incorreta -> UPDATE chave valor")
            print("chave: INTEGER")
            print("valor: STRING")
    elif toks[0].lower() == "delete":
        if len(toks) == 2 and toks[1].isdigit():
            cli_socket.send(bytes(sentence, "utf-8"))
        else:
            print("ERRO: sintaxe incorreta -> DELETE chave")
            print("chave: INTEGER")
    elif toks[0].lower() == "exit":
        cli_socket.send(bytes(sentence, "utf-8"))
        keep_alive = False
    else:
        print("Comando desconhecido!!!")

print("Encerrando...")

receiver.join()
cli_socket.close()
