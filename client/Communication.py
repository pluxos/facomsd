import threading
import os
from datetime import datetime
from Connection import Connection


class Communication(threading.Thread, Connection):

    def __init__(self, requests, responses):
        threading.Thread.__init__(self)
        Connection.__init__(self, responses)
        self.stopRequest = threading.Event()
        self.newMessage = threading.Event()
        self.requests = requests
        self.responses = responses

    def run(self):
        print("Starting Thread of Communication...")

        while not self.stopRequest.isSet():
            if self.newMessage.isSet():
                self.send()
                self.newMessage.clear()
        print("Stop Thread of Communication in ", datetime.now())

    def new_message(self, msg):
        self.requests.put(msg)
        self.newMessage.set()

    def send(self):
        msg = self.requests.get()
        print(msg)
        print(type(msg))

        if msg[0].upper() == "READ":
            self.read(msg[1])
        elif msg[0].upper() == "CREATE":
            self.write(msg[1], msg[2])
        elif msg[0].upper() == "UPDATE":
            self.update(msg[1], msg[2])
        elif msg[0].upper() == "DELETE":
            self.delete(msg[1])

    def join(self, timeout=None):
        print("Request Stop in ", datetime.now())
        super(Communication, self).join(timeout)
        self.socket.close()
        self.stopRequest.set()
