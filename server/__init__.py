from __future__ import absolute_import
import sys
sys.path.append(u'../grpcDefinitions')
sys.path.append(u'./chord')

from Queue import Queue

from reloadDatabase import ReloadDatabase
from persistent import Persistent
from listener   import Listener
from splitter   import Splitter
from logger     import Logger
from asyncService import AsyncService
from redirector import Redirector

from node import Node

from time import sleep

class Server(AsyncService):

    def __init__(self, threadName=u"Server"):
        AsyncService.__init__(self)
        self.requests = Queue()
        self.waitLog = Queue()
        self.waitPersist = Queue()
        self.waitRedirect = Queue()

        self.setName(threadName)

        self.node = Node(threadName)

        self.splitter = Splitter(self.node, self.requests,
                                 self.waitLog, self.waitPersist,
                                 self.waitRedirect, threadName
                        )

        self.persistent = Persistent(self.waitPersist, threadName)

        self.reloadDatabase = ReloadDatabase(self.persistent, self.splitter)

        self.logger = Logger(self.waitLog, threadName, self.reloadDatabase)

        self.redirector = Redirector(self.node, self.waitRedirect, threadName)

        self.listener = Listener(self.requests, threadName)

    def run(self):

        while not self.persistent.isLoaded.isSet():
            sleep(0.1)
        self.node.start()
        self.splitter.start()
        self.redirector.start()
        self.logger.start()
        self.persistent.start()
        self.listener.start()
        self.reloadDatabase.start()

        while not self.stopEvent.isSet():
            sleep(2)
        self.stop()
        self.stopEvent.clear()
        self.stopFinish.set()

    def stop(self):
        self.persistent.join()
        self.logger.join()
        self.splitter.join()
        self.listener.join()
        self.redirector.join()



if __name__ == u"__main__":
    Server().start()
    while True:
        sleep(24*60*60)
        pass