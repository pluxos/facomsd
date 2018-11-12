import sys
import configparser
import os

sys.path.append('../grpcDefinitions')

from concurrent import futures
from grpc import server as grpc_server
from time import sleep

from asyncService import AsyncService
from server import Server
from sd_work_pb2_grpc import ServerServicer, add_ServerServicer_to_server


CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')
timeToSleep = 2


class Listener(AsyncService):

    def __init__(self, requests, threadName):
        AsyncService.__init__(self)
        self.host = CONFIG.get('all', 'host')
        self.port = str(CONFIG.getint('all', 'PORT'))

        self.requests = requests

        self.recipients = []
        self.setName(threadName)

    def run(self):
        server = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_ServerServicer_to_server(Server(self.requests), server)
        server.add_insecure_port("0.0.0.0:" + self.port)
        server.start()
        try:
            print("I'm going sleep")
            while not self.stopEvent.isSet():
                sleep(timeToSleep)
        except KeyboardInterrupt:
            server.stop(0)
        self.stopEvent.clear()
        self.stopFinish.set()
