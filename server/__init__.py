#import config
from queue import Queue

from reloadDatabase import ReloadDatabase
from persistent import Persistent
from listener   import Listener
from splitter   import Splitter
from logger     import Logger
from asyncService import AsyncService

import socket
import signal


#stop(None, None)


class Server(AsyncService):

    def __init__(self):
        AsyncService.__init__(self)
        self.requests = Queue()
        self.waitLog = Queue()
        self.waitPersist = Queue()

        self.loadDatabase = ReloadDatabase(self.requests)

        self.splitter = Splitter(self.requests, self.waitLog, self.waitPersist)

        self.logger = Logger(self.waitLog)

        self.persistent = Persistent(self.waitPersist)

        # Start Listerner in new Thread
        self.listener = Listener(self.requests)

    def run(self):

        self.loadDatabase.load()
        self.splitter.start()
        self.logger.start()
        self.persistent.start()
        self.listener.start()

        while not self.stopEvent.isSet():
            pass
        self.stop()
        self.stopFinish.set()

    def stop(self):
        self.logger.join()
        self.splitter.join()
        self.listener.join()



if __name__ == "__main__":
    Server().start()