import threading
import socket
from datetime import datetime
from connection import Connection


class Listener(threading.Thread, Connection):

    def __init__(self, socket):
        Connection.__init__(self, socket)
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()

    def run(self):
        print("Starting Thread of Communication...")

        while not self.stopRequest.isSet():
            msg = self.getResponse()

            if msg is not None and len(msg) == 0:
                self.reconnect()
            if msg is not None:
                print(msg)
        self.connection.close()
        print("Stop Thread of Communication in ", datetime.now())

    def join(self, timeout=None):
        print("Request Stop in ", datetime.now())
        super(Listener, self).join(timeout)
        self.stopRequest.set()
