from receptor import Receptor
from asyncService import AsyncService

import socket
import configparser


CONFIG = configparser.ConfigParser()
CONFIG.read('../config.py')


class Listener(AsyncService):

    def __init__(self, requests):
        AsyncService.__init__(self)
        self.socket = socket.socket()

        self.host = socket.gethostname()
        self.port = CONFIG.getint('all', 'PORT')


        self.socket.bind((self.host, self.port))
        self.socket.listen(1)

        self.requests = requests

        self.recipients = []

    def run(self):
        self.socket.settimeout(5)
        try:
            while not self.stopEvent.isSet():
                try:
                    connection, address = self.socket.accept()
                except socket.timeout:
                    continue
                connection.settimeout(5)

                recep = Receptor(connection, address, self.requests)
                self.recipients.append(recep)

                print("Connection accepted with ", address)
                recep.start()
        finally:
            for r in self.recipients:
                r.join()

            self.socket.close()
        print("Exiting Listener")
        self.stopEvent.clear()
