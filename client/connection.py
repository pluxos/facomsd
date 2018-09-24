from time import sleep
from mysocket import Socket
import socket
import configparser
import os
CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')

class Connection:

    def __init__(self, socket):
        self.connection = socket

    def sendRequest(self, msg):
        send = False
        while not send:
            try:
                self.connection.socket.send(msg)
                send = True
            except socket.error:
                print("Retrying send " + msg.decode())
                sleep(0.5)

    def getResponse(self):
        buffer = ""
        BUFFERSIZE = 1024
        self.connection.socket.settimeout(5)
        while True:
            try:
                msg = self.connection.socket.recv(BUFFERSIZE)
                if len(msg) == BUFFERSIZE:
                    buffer += msg.decode()
                else:
                    buffer += msg.decode()
                    break
            except socket.timeout:
                return None
            except socket.error:
                self.reconnect()
        return buffer

    def reconnect(self):
        self.connection.reconnect()


def createSocket():
    s = Socket()
    return s
