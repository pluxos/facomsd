from __future__ import absolute_import
import threading

from Queue import Empty
from asyncService import AsyncService
from threading import Event
from time import sleep

class Splitter(AsyncService):

    def __init__(self, node, requests, toLog, toPersist, toRedirect, threadName):
        AsyncService.__init__(self)
        self.requests = requests
        self.toLog = toLog
        self.toRedirect = toRedirect
        self.toPersist = toPersist
        self.pause = Event()
        self.node = node


    def run(self):

        while not self.stopEvent.isSet():
            try:
                while self.pause.isSet():
                    # print("I'm Paused!")
                    sleep(0.1)
                connection, request = self.requests.get(True, 2)

                id = int(request[1].id.decode())

                if self.node.verify_responsibility(id):
                    self.toPersist.put((connection, request))
                    # if request[0].upper() != u"READ" and connection is not None:
                    #     self.toLog.put(request)
                else:
                    self.toRedirect.put((connection, request))
            except Empty:
                continue
        print u"Exiting Splitter"
        self.stopEvent.clear()
        self.stopFinish.set()
