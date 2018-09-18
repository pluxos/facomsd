import threading


class Receptor(threading.Thread):

    def __init__(self, connection, address, requests):
        threading.Thread.__init__(self)
        self.connection = connection
        self.address = address
        self.requests = requests

    def run(self):
        BUFFERSIZE = 1024
        buffer = []
        try:
            while True:
                msg = self.connection.recv(BUFFERSIZE)
                if len(msg) == BUFFERSIZE:
                    buffer += msg
                elif len(msg) == 0:
                    break
                else:
                    self.connection.send("ok".encode())
                    print(msg.decode())
                    buffer.clear()
        finally:
            self.connection.close()
            print("Close Connection with ", self.address)
