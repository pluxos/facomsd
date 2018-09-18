import socket
import configparser

class Connection:

    def __init__(self, socket):
        self.socket = socket

    def sendRequest(self, msg):
        self.socket.send(msg)

    def getResponse(self):
        buffer = ""
        BUFFERSIZE = 1024
        self.socket.settimeout(5)
        while True:
            print("read Socket!")
            try:
                msg = self.socket.recv(BUFFERSIZE)
            except socket.timeout:
                break
            if len(msg) == BUFFERSIZE:
                buffer += msg.decode()
            else:
                buffer += msg.decode()
                break

        return buffer


def createSocket():
    CONFIG = configparser.ConfigParser()
    CONFIG.read('../config.py')

    s = socket.socket()
    s.connect((CONFIG.get('all', 'HOST'), CONFIG.getint('all', 'PORT')))
    return s
