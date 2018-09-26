from commands import Commands
from listenerClient import Listener
from connection import createSocket
import threading


class Client(threading.Thread):
    def __init__(self, threadName='client'):
        threading.Thread.__init__(self)
        self.socket = createSocket()

        self.listener = Listener(self.socket, threadName)
        self.command = Commands(self.socket, threadName)
        self.setName(threadName)

    def run(self):
        self.command.start()
        self.listener.start()

        self.command.join()
        self.listener.stopListen(5.0)


if __name__ == "__main__":
    Client().start()