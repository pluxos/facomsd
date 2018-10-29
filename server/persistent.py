import sys

sys.path.append('../grpcDefinitions')

from asyncService import AsyncService
from reloadDatabase import ReloadDatabase

from queue import Empty
from grpc import StatusCode
from sd_work_pb2 import Data, ServerResponse
from threading import Event

class Persistent(AsyncService):

    def __init__(self, toPersist, threadName):
        AsyncService.__init__(self)
        self.isLoaded = Event()
        self.pause = Event()
        self.toPersist = toPersist
        self.data = {}
        self.setName(threadName)

    def run(self):

        while not self.stopEvent.isSet():

            try:
                connection, request = self.toPersist.get(True, 1)

                r = request[0].upper()
                #print(r)
                if r == "READ":
                    self.read(request[1], connection)
                elif r == "CREATE":
                    self.create(request[1], connection)
                elif r == "UPDATE":
                    self.update(request[1], connection)
                elif r == "DELETE":
                    self.delete(request[1], connection)
                else:
                    print("request failed")

            except Empty:
                continue
            except Exception as e:
                print("Exception: ", e)
        self.stopEvent.clear()
        self.stopFinish.set()

    def read(self, request, connection):
        if connection is not None:
            id = int(request.id.decode())
            if self.data.get(id) is not None:
                connection.put(ServerResponse(data=Data(id=str(id).encode(), data=self.data[id]), message="Data Found!"))
            else:
                connection.put(StatusCode.NOT_FOUND)

    def create(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is None:
            self.data[id] = request.data
            if connection is not None:
                connection.put(ServerResponse(message="Data created"))
            else:
                print("create " + str(id) + ": " + request.data)
        else:
            if connection is not None:
                connection.put(StatusCode.ALREADY_EXISTS)
            else:
                print("nOK to create ", id)

    def update(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is not None:
            self.data[id] = request.data
            if connection is not None:
                connection.put(ServerResponse(message="Data Updated!"))
            else:
                print("update " + str(id) + ": " + request.data)
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
            else:
                print("nOK - to update" + str(id))

    def delete(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is not None:
            self.data.pop(id, None)
            if connection is not None:
                connection.put(ServerResponse(message="Data Deleted!"))
            else:
                print("delete " + str(id))
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
            else:
                print("nOK - to delete" + str(id))

