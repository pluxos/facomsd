from Receptor import Receptor
import socket
import configparser


CONFIG = configparser.ConfigParser()
CONFIG.read('config.py')


class Listener:

    def __init__(self, requests):
        self.socket = socket.socket()

        self.host = socket.gethostname()
        self.port = CONFIG.getint('all', 'PORT')

        self.socket.bind((self.host, self.port))
        self.socket.listen(1)

        self.requests = requests

        self.recipients = []

    def listen(self):
        try:
            while True:
                connection, address = self.socket.accept()

                recep = Receptor(connection, address, self.requests)
                self.recipients.append(recep)

                print("Connection accepted with ", address)
                recep.start()
        finally:
            self.socket.close()
