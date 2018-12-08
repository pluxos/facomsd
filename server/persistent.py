from __future__ import absolute_import
import sys

sys.path.append(u'../grpcDefinitions')

from asyncService import AsyncService
from reloadDatabase import ReloadDatabase

from Queue import Empty
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
                connection, request = self.toPersist.get(True, 2)

                r = request[0].upper()
                #print(r)
                if r == u"READ":
                    self.read(request[1], connection)
                elif r == u"CREATE":
                    self.create(request[1], connection)
                elif r == u"UPDATE":
                    self.update(request[1], connection)
                elif r == u"DELETE":
                    self.delete(request[1], connection)
                else:
                    print u"request failed"

            except Empty:
                continue
            except Exception, e:
                print u"Exception: ", e
        self.stopEvent.clear()
        self.stopFinish.set()

    def read(self, request, connection):
        if connection is not None:
            id = int(request.id.decode())
            if self.data.get(id) is not None:
                connection.put(ServerResponse(data=Data(id=unicode(id).encode(), data=self.data[id]), message=u"Data Found!"))
            else:
                connection.put(StatusCode.NOT_FOUND)

    def create(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is None:
            self.data[id] = request.data
            if connection is not None:
                connection.put(ServerResponse(message=u"Data created"))
            else:
                print u"create " + unicode(id) + u": " + request.data
        else:
            if connection is not None:
                connection.put(StatusCode.ALREADY_EXISTS)
            else:
                print u"nOK to create ", id

    def update(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is not None:
            self.data[id] = request.data
            if connection is not None:
                connection.put(ServerResponse(message=u"Data Updated!"))
            else:
                print u"update " + unicode(id) + u": " + request.data
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
            else:
                print u"nOK - to update" + unicode(id)

    def delete(self, request, connection):
        id = int(request.id.decode())
        if self.data.get(id) is not None:
            self.data.pop(id, None)
            if connection is not None:
                connection.put(ServerResponse(message=u"Data Deleted!"))
            else:
                print u"delete " + unicode(id)
        else:
            if connection is not None:
                connection.put(StatusCode.NOT_FOUND)
            else:
                print u"nOK - to delete" + unicode(id)

