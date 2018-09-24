from commands import Commands
from listenerClient import Listener
from connection import createSocket
import threading


class Client(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.socket = createSocket()

        self.listener = Listener(self.socket)
        self.command = Commands(self.socket)

    def run(self):
        self.command.start()
        self.listener.start()

        self.command.join()
        self.listener.join(5.0)


if __name__ == "__main__":
    Client().start()