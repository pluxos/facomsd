from receptor import Receptor
from asyncService import AsyncService

import socket
import configparser

import os
CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')


class Listener(AsyncService):

    def __init__(self, requests, threadName):
        AsyncService.__init__(self)
        self.socket = socket.socket()

        self.socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.host = socket.gethostname()
        self.port = CONFIG.getint('all', 'PORT')

        self.socket.bind((self.host, self.port))
        self.socket.listen(1)

        self.requests = requests

        self.recipients = []
        self.setName(threadName)

    def run(self):
        self.socket.settimeout(1)
        try:
            while not self.stopEvent.isSet():
                try:
                    connection, address = self.socket.accept()
                except socket.timeout:
                    continue
                connection.settimeout(1)

                recep = Receptor(connection, address, self.requests, self.getName())
                self.recipients.append(recep)

                print("Connection accepted with ", address)
                recep.start()
        finally:
            for r in self.recipients:
                r.join()

            self.socket.close()
        print("Exiting Listener")
        self.stopEvent.clear()
        self.stopFinish.set()
