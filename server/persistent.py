from asyncService import AsyncService

from queue import Empty
import socket


class Persistent(AsyncService):

    def __init__(self, toPersist, threadName):
        AsyncService.__init__(self)
        self.toPersist = toPersist
        self.data = {}
        self.setName(threadName)


    def run(self):

        while not self.stopEvent.isSet():

            try:
                request, connection = self.toPersist.get(True, 1)

                r = request.split()
                if r[0].upper() == "READ":
                    self.read(request, connection)
                elif r[0].upper() == "CREATE":
                    self.create(request, connection)
                elif r[0].upper() == "UPDATE":
                    self.update(request, connection)
                elif r[0].upper() == "DELETE":
                    self.delete(request, connection)

            except Empty:
                continue
            except socket.error:
                continue
        self.stopEvent.clear()
        self.stopFinish.set()

    def read(self, request, connection):
        r = request.split()
        if connection is not None:
            if self.data.get(r[1]) is not None:
                connection.send(("OK - " + r[1] + ": " + self.data[r[1]] +"$\n").encode())
            else:
                connection.send(("nOK -" + r[1] + " not found!$\n").encode())

    def create(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is None:
            self.data[r[1]] = r[2]
            if connection is not None:
                connection.send(("OK - " + r[1] + " created!$\n").encode())
            else:
                print("create " + r[1] + ": " + r[2])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " already exists!$\n").encode())
            else:
                print("nOK to create " + r[1])

    def update(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is not None:
            self.data[r[1]] = r[2]
            if connection is not None:
                connection.send(("OK - " + r[1] + " updated!$\n").encode())
            else:
                print("update " + r[1] + ": " + r[2])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " not found!$\n").encode())
            else:
                print("nOK - to update" + r[1])

    def delete(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is not None:
            self.data.pop(r[1], None)
            if connection is not None:
                connection.send(("OK - " + r[1] + " deleted!$\n").encode())
            else:
                print("delete " + r[1])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " not found!$\n").encode())
            else:
                print("nOK - to delete" + r[1])

