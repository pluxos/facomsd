import threading

from queue import Empty
from asyncService import AsyncService


class Splitter(AsyncService):

    def __init__(self, requests, toLog, toPersist):
        AsyncService.__init__(self)
        self.requests = requests
        self.toLog = toLog
        self.toPersist = toPersist
        self.stopEvent = threading.Event()


    def run(self):

        while not self.stopEvent.isSet():
            try:
                request = self.requests.get(True, 1)
                self.toPersist.put(request)
                self.toLog.put(request)
            except Empty:
                continue
        print("Exiting Splitter")
        self.stopEvent.clear()