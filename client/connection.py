from time import sleep
from mysocket import Socket
import socket
import configparser

CONFIG = configparser.ConfigParser()
CONFIG.read('../config.py')

class Connection:

    def __init__(self, socket):
        self.connection = socket

    def sendRequest(self, msg):
        self.connection.socket.send(msg)

    def getResponse(self):
        buffer = ""
        BUFFERSIZE = 1024
        self.connection.socket.settimeout(5)
        while True:
            try:
                msg = self.connection.socket.recv(BUFFERSIZE)
            except socket.timeout:
                return None
            if len(msg) == BUFFERSIZE:
                buffer += msg.decode()
            else:
                buffer += msg.decode()
                break

        return buffer

    def reconnect(self):
        self.connection.reconnect()


def createSocket():
    s = Socket()
    return s
