from __future__ import absolute_import
from asyncService import AsyncService
import socket


class Receptor(AsyncService):

    def __init__(self, connection, address, requests, threadName):
        AsyncService.__init__(self)
        self.connection = connection
        self.address = address
        self.requests = requests
        self.setName(threadName)

    def run(self):
        BUFFERSIZE = 1024
        buffer = u""
        try:
            while not self.stopEvent.isSet():
                try:
                    msg = self.connection.recv(BUFFERSIZE)
                    if len(msg) == BUFFERSIZE:
                        buffer += msg.decode()
                    elif len(msg) == 0:
                        break
                    else:
                        buffer += msg.decode()
                        buffer = buffer.split(u'$\n')
                        for b in buffer:
                            if len(b) > 0:
                                b = b.replace(u'\n', u'')
                                #print("receive : " + b)
                                self.requests.put((b, self.connection))
                        buffer = u""
                except socket.timeout:
                    continue
        finally:
            self.connection.close()
            print u"Close Connection with ", self.address
        print u"Exiting Receptor"
        self.stopEvent.clear()
        self.stopFinish.set()
