import threading

from queue import Empty
from asyncService import AsyncService
from threading import Event
from time import sleep


class Splitter(AsyncService):

    def __init__(self, requests, toLog, toPersist, threadName):
        AsyncService.__init__(self)
        self.requests = requests
        self.toLog = toLog
        self.toPersist = toPersist
        self.pause = Event()
        self.setName(threadName)


    def run(self):

        while not self.stopEvent.isSet():
            try:
                while self.pause.isSet():
                    print("I'm Paused!")
                    sleep(0.1)
                connection, request = self.requests.get(True, 1)
                self.toPersist.put((connection, request))
                if request[0].upper() != "READ" and connection is not None:
                    self.toLog.put(request)
            except Empty:
                continue
        print("Exiting Splitter")
        self.stopEvent.clear()
        self.stopFinish.set()
