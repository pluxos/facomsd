from asyncService import AsyncService

from queue import Empty


class Persistent(AsyncService):

    def __init__(self, toPersist):
        AsyncService.__init__(self)
        self.toPersist = toPersist
        self.data = {}


    def run(self):

        while not self.stopEvent.isSet():

            try:
                request, connection = self.toPersist.get(True, 1)

                r = request.split()

                if r[0].upper() == "READ":
                    self.read(request, connection)
                if r[0].upper() == "CREATE":
                    self.create(request, connection)
                if r[0].upper() == "UPDATE":
                    self.update(request, connection)
                if r[0].upper() == "DELETE":
                    self.delete(request, connection)

            except Empty:
                continue
        for k,v in self.data:
            print(k + ": " + v)

    def read(self, request, connection):
        r = request.split()
        if connection is not None:
            if self.data.get(r[1]) is not None:
                connection.send((r[1] + ": " + self.data[r[1]]).encode())
            else:
                connection.send(("nOK -" + r[1] + " not found!").encode())

    def create(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is None:
            self.data[r[1]] = r[2]
            if connection is not None:
                connection.send(("OK - " + r[1] + " created!").encode())
            else:
                print("create " + r[1] + ": " + r[2])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " already exists!").encode())
            else:
                print("nOK to create " + r[1])

    def update(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is not None:
            self.data[r[1]] = r[2]
            if connection is not None:
                connection.send(("OK - " + r[1] + " updated!").encode())
            else:
                print("update " + r[1] + ": " + r[2])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " not found!").encode())
            else:
                print("nOK - to update" + r[1])

    def delete(self, request, connection):
        r = request.split()
        if self.data.get(r[1]) is not None:
            self.data.pop(r[1], None)
            if connection is not None:
                connection.send(("OK - " + r[1] + " deleted!").encode())
            else:
                print("delete " + r[1])
        else:
            if connection is not None:
                connection.send(("nOK - " + r[1] + " not found!").encode())
            else:
                print("nOK - to delete" + r[1])

