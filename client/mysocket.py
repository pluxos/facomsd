from time import sleep
import socket
import configparser


CONFIG = configparser.ConfigParser()
CONFIG.read('../config.py')


class Socket:

    def __init__(self):
        self.socket = socket.socket()
        self.socket.connect((CONFIG.get('all', 'HOST'), CONFIG.getint('all', 'PORT')))

    def close(self):
        self.socket.close()

    def reconnect(self):
        print("connection lost...")
        print("reconnecting...")
        connected = False
        while not connected:
            try:
                print("Trying connect...")
                self.socket = socket.socket()
                self.socket.connect((CONFIG.get('all', 'HOST'), CONFIG.getint('all', 'PORT')))
                connected = True
                print("Reconnected!")
            except socket.error:
                sleep(2)