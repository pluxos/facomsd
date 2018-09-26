""" Codigo da aplicacao servidor """
import queue
import socket
import utils


arquivo = open("C:\\Users\\Luis Carlos\\Documents\\Projetos\\SD\\configs.ini", "r")
configs = eval(arquivo.read())
arquivo.close()

port = configs.get("port", 12345)
host = socket.gethostname()

server_socket = socket.socket()
server_socket.bind((host, port))

# criando um banco de dados em memoria para armazenar os dados
db = {}

# alocando filas para armazenar e tratar as requisicoes
reqs = queue.Queue() # fila F1
logq = queue.Queue() # fila F2
proc = queue.Queue() # fila F3

# alocando alguns threads para manipular as requisicoes
consumidor = utils.Consumidor(reqs, [logq, proc])

# iniciando as threads
consumidor.start()

server_socket.listen(1)

while True:
    # aceitando se comunicar com a aplicacao cliente
    c, addr = server_socket.accept()

    print("Got connection from {0}!!!".format(addr))

    # delegando a conexao com o cliente para uma
    # thread para tratar as requisicoes
    utils.Requisicoes(c, addr, reqs).start()
    utils.Processamento(c, addr, proc, db).start()
