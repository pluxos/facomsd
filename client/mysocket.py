from time import sleep
import socket
import configparser

import os
CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')


class Socket:

    def __init__(self):
        try:
            self.socket = socket.socket()
            self.socket.connect((CONFIG.get('all', 'HOST'), CONFIG.getint('all', 'PORT')))
        except socket.error:
            self.reconnect()

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