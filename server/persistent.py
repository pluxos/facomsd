import sys

sys.path.append('../grpcDefinitions')

from asyncService import AsyncService

from queue import Empty
from grpc import StatusCode
from sd_work_pb2 import Data, ServerResponse

class Persistent(AsyncService):

    def __init__(self, toPersist, threadName):
        AsyncService.__init__(self)
        self.toPersist = toPersist
        self.data = {}
        self.setName(threadName)


    def run(self):

        while not self.stopEvent.isSet():

            try:
                connection, request = self.toPersist.get(True, 1)

                r = request[0].upper()
                print(r)
                if r == "READ":
                    self.read(request, connection)
                elif r == "CREATE":
                    self.create(request, connection)
                elif r == "UPDATE":
                    self.update(request, connection)
                elif r == "DELETE":
                    self.delete(request, connection)
                else:
                    print("request failed")

            except Empty:
                continue
        self.stopEvent.clear()
        self.stopFinish.set()

    def read(self, request, connection):
        if connection is not None:
            id = int(request[1].id.decode())
            if self.data.get(id) is not None:
                connection.put(ServerResponse(data=Data(id=str(id).encode(), data=self.data[id]), message="Data Found!"))
                #connection.send(("OK - " + request.id + ": " + self.data[r[1]] +"$\n").encode())
            else:
                connection.put(StatusCode.NOT_FOUND)
                #connection.send(("nOK -" + request.id + " not found!$\n").encode())

    def create(self, request, connection):
        id = int(request[1].id.decode())
        if self.data.get(id) is None:
            self.data[id] = request[1].data
            if connection is not None:
                connection.put(ServerResponse(message="Data created"))
                #connection.send(("OK - " + r[1] + " created!$\n").encode())
            else:
                print("create " + str(id) + ": " + request[1].data)
        else:
            if connection is not None:
                connection.put(StatusCode.ALREADY_EXISTS)
                #connection.send(("nOK - " + r[1] + " already exists!$\n").encode())
            else:
                print("nOK to create ", id)

    def update(self, request, connection):
        id = int(request[1].id.decode())
        if self.data.get(id) is not None:
            self.data[id] = request[1].data
            if connection is not None:
                connection.put(ServerResponse(message="Data Updated!"))
                #connection.send(("OK - " + r[1] + " updated!$\n").encode())
            else:
                print("update " + str(id) + ": " + request[1].data)
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
                #connection.send(("nOK - " + r[1] + " not found!$\n").encode())
            else:
                print("nOK - to update" + str(id))

    def delete(self, request, connection):
        id = int(request[1].id.decode())
        if self.data.get(id) is not None:
            self.data.pop(id, None)
            if connection is not None:
                connection.put(ServerResponse(message="Data Deleted!"))
                #connection.send(("OK - " + r[1] + " deleted!$\n").encode())
            else:
                print("delete " + str(id))
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
                #connection.send(("nOK - " + r[1] + " not found!$\n").encode())
            else:
                print("nOK - to delete" + str(id))

