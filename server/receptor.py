from asyncService import AsyncService
import socket


class Receptor(AsyncService):

    def __init__(self, connection, address, requests):
        AsyncService.__init__(self)
        self.connection = connection
        self.address = address
        self.requests = requests

    def run(self):
        BUFFERSIZE = 1024
        buffer = ""
        try:
            while not self.stopEvent.isSet():
                try:
                    msg = self.connection.recv(BUFFERSIZE)
                    if len(msg) == BUFFERSIZE:
                        buffer += msg.decode()
                    elif len(msg) == 0:
                        break
                    else:
                        buffer += msg.decode()
                        self.requests.put((buffer, self.connection))
                        buffer = ""
                except socket.timeout:
                    continue
        finally:
            self.connection.close()
            print("Close Connection with ", self.address)
        print("Exiting Receptor")
        self.stopEvent.clear()
