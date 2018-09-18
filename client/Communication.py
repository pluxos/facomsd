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
                try:
                    self.send()
                except Exception as error:
                    self.responses.put(str(error))
                finally:
                    self.newMessage.clear()
        print("Stop Thread of Communication in ", datetime.now())

    def new_message(self, msg):
        self.requests.put(msg)
        self.newMessage.set()

    def send(self):
        msg = self.requests.get()

        if msg[0].upper() == "READ":
            self.read(msg[1:])
        elif msg[0].upper() == "CREATE":
            self.create(msg[1:])
        elif msg[0].upper() == "UPDATE":
            self.update(msg[1:])
        elif msg[0].upper() == "DELETE":
            self.delete(msg[1:])

    def join(self, timeout=None):
        print("Request Stop in ", datetime.now())
        super(Communication, self).join(timeout)
        self.socket.close()
        self.stopRequest.set()

    def read(self, id):
        if len(id) > 1:
            raise Exception("invalid arguments! (READ ID)")
        try:
            int(id[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('read ' + id[0]).encode())

    def create(self, args):
        if len(args) != 2:
            raise Exception("invalid arguments! (CREATE ID VALUE)")
        try:
            int(args[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('create ' + args[0] + " " + args[1]).encode())

    def update(self, args):
        if len(args) != 2:
            if len(args) != 2:
                raise Exception("invalid arguments! (UPDATE ID VALUE)")
        try:
            int(args[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('update ' + args[0] + " " + args[1]).encode())

    def delete(self, id):
        print(id)
        if len(id) > 1:
            raise Exception("invalid arguments! (DELETE ID)")
        try:
            int(id[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('delete ' + id[0]).encode())