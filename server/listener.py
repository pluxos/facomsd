from __future__ import absolute_import
import sys
import ConfigParser
import os

sys.path.append(u'../grpcDefinitions')

from concurrent import futures
from grpc import server as grpc_server
from time import sleep

from asyncService import AsyncService
from server import Server
from sd_work_pb2_grpc import ServerServicer, add_ServerServicer_to_server


CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../config.py')
timeToSleep = 2


class Listener(AsyncService):

    def __init__(self, requests, threadName):
        AsyncService.__init__(self)
        self.host = CONFIG.get(u'all', u'host')
        self.port = unicode(CONFIG.getint(u'all', u'PORT'))

        self.requests = requests

        self.recipients = []
        self.setName(threadName)

    def run(self):
        server = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_ServerServicer_to_server(Server(self.requests), server)
        server.add_insecure_port(u"0.0.0.0:" + self.port)
        server.start()
        try:
            print u"I'm going sleep"
            while not self.stopEvent.isSet():
                sleep(timeToSleep)
        except KeyboardInterrupt as e:
            print 'KeyboardInterrupt --> ' + str(e)
            server.stop(0)
        self.stopEvent.clear()
        self.stopFinish.set()
