import threading


class Receptor(threading.Thread):

    def __init__(self, connection, address, requests):
        threading.Thread.__init__(self)
        self.connection = connection
        self.address = address
        self.requests = requests

    def run(self):

        while True:
            msg = self.connection.recv(1024)
            if len(msg) == 0:
                self.connection.close()
                print("Close Connection with ", self.address)
                break
            print(len(msg))
            print(msg.decode())
            #self.connection.close()
            #break
