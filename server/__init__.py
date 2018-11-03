import sys
sys.path.append('../grpcDefinitions')
sys.path.append('./chord')

from queue import Queue

from reloadDatabase import ReloadDatabase
from persistent import Persistent
from listener   import Listener
from splitter   import Splitter
from logger     import Logger
from asyncService import AsyncService

from node import Node

from time import sleep

class Server(AsyncService):

    def __init__(self, threadName="Server"):
        AsyncService.__init__(self)
        self.requests = Queue()
        self.waitLog = Queue()
        self.waitPersist = Queue()

        self.setName(threadName)

        self.node = Node(threadName)

        self.splitter = Splitter(self.requests, self.waitLog, self.waitPersist, threadName)

        self.persistent = Persistent(self.waitPersist, threadName)

        self.reloadDatabase = ReloadDatabase(self.persistent, self.splitter)

        self.logger = Logger(self.waitLog, threadName, self.reloadDatabase)


        self.listener = Listener(self.requests, threadName)

    def run(self):

        while not self.persistent.isLoaded.isSet():
            sleep(0.1)
        self.node.start()
        self.splitter.start()
        self.logger.start()
        self.persistent.start()
        self.listener.start()
        self.reloadDatabase.start()

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