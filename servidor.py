import queue
import socket
import utils

arquivo = open("C:\\Users\\Luis Carlos\\Documents\\GitHub\\sd\\facomsd\\configs.ini", "r")
configs = eval(arquivo.read())
arquivo.close()

host = socket.gethostname()
port = configs.get("port", 12345)
logfile = open(configs.get("log"))

# criando o socket do servidor e associando o servidor a uma porta
server_socket = socket.socket()
server_socket.bind((host, port))

# alocando um banco de dados em memoria para armazenar os itens
db = {}

# alocando as filas para armazenar os processamento das requisicoes
# enviado pelos usuarios
reqs = queue.Queue() # F1 (requisicoes)
logs = queue.Queue() # F2 (log)
proc = queue.Queue() # F3 (processamento)

# instanciando as threads para serem usados no sistema
consumidor = utils.Consumidor(reqs, [logs, proc])
processamento = utils.Processamento(proc, db)
logger = utils.Logger(logs, logfile)

# inicializando as threads
consumidor.start()
processamento.start()

server_socket.listen(1)

# laco principal da execucao do servidor de aplicacao
while True:
    conn, addr = server_socket.accept()
    print("Got connection from {0}...".format(addr))
    # criando uma nova thread para processar as requisicoes
    # dos clientes
    utils.Requisicoes(conn, addr, reqs).start()

server_socket.close()
