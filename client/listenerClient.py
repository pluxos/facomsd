import threading
import socket
from datetime import datetime
from time import sleep
from connection import Connection


class Listener(threading.Thread, Connection):

    def __init__(self, socket, threadName):
        Connection.__init__(self, socket)
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()
        self.setName(threadName)
        self.daemon = True

    def run(self):
        print("Starting Thread of Communication...")

        while not self.stopRequest.isSet():
            msg = self.getResponse()
            if msg is not None and len(msg) == 0:
                self.reconnect()
            if msg is not None:
                for m in msg:
                    if len(m) > 0:
                        print(m)
        self.connection.close()
        print("Stop Thread of Communication in ", datetime.now())

    def stopListen(self, timeout=None):
        print("Request Stop in ", datetime.now())
        # super(Listener, self).join(timeout)
        if timeout is None:
            return
        sleep(timeout)
        self.stopRequest.set()
