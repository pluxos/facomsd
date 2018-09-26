""" Codificando uma aplicacao cliente simples """
import socket


arquivo = open("C:\\Users\\Luis Carlos\\Documents\\Projetos\\SD\\configs.ini", "r")
configs = eval(arquivo.read())
arquivo.close()

port = configs.get("port", 12345)
host = socket.gethostname()
client_socket = socket.socket()
client_socket.connect((host, port))

"""
mensagem = input("Digite um comando: ")
# enviando a mensagem para o servidor
client_socket.send(bytes(mensagem, "utf-8"))
"""

while True:
    comando = input("Digite um comando: ")
    # enviando o comando para o servidor
    if comando != "exit":
        client_socket.send(bytes(comando, "utf-8"))
        resposta = client_socket.recv(4096).decode("utf-8")
        print(resposta)
    else:
        break

client_socket.close()
