from queue import Queue

from reloadDatabase import ReloadDatabase
from persistent import Persistent
from listener   import Listener
from splitter   import Splitter
from logger     import Logger
from asyncService import AsyncService

from time import sleep

class Server(AsyncService):

    def __init__(self, threadName="Server"):
        AsyncService.__init__(self)
        self.requests = Queue()
        self.waitLog = Queue()
        self.waitPersist = Queue()

        self.setName(threadName)
        self.loadDatabase = ReloadDatabase(self.requests)

        self.splitter = Splitter(self.requests, self.waitLog, self.waitPersist, threadName)

        self.logger = Logger(self.waitLog, threadName)

        self.persistent = Persistent(self.waitPersist, threadName)

        self.listener = Listener(self.requests, threadName)

    def run(self):

        self.loadDatabase.load()
        self.splitter.start()
        self.logger.start()
        self.persistent.start()
        self.listener.start()

        while not self.stopEvent.isSet():
            sleep(1)
        self.stop()
        self.stopEvent.clear()
        self.stopFinish.set()

    def stop(self):
        self.persistent.join()
        self.logger.join()
        self.splitter.join()
        self.listener.join()



if __name__ == "__main__":
    Server().start()
    while True:
        pass