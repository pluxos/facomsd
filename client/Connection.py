import socket
from time import sleep

class Connection:

    def __init__(self, responses):
        print("Connect")
        self.socket = socket.socket()
        self.socket.connect(('localhost', 12345))

        self.responses = responses

    def read(self, id):
        print('Doing read in ' + id)
        sleep(5)
        self.responses.put("blablabla")

