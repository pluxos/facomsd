import sys
import configparser
import os
import socket
from concurrent import futures
from time import sleep
from grpc import server as grpc_server

from chord_communication import Chord
from server_side_pb2_grpc import add_P2PServicer_to_server
from asyncService import AsyncService

CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../../config.py')

timeToSleep = 2

class Node(AsyncService):

    def __init__(self, threadName):
        AsyncService.__init__(self)
        self.chord = Chord()

        self.port = str(CONFIG.getint('p2p', 'PORT'))
        self.next = None
        self.back = None
        self.fingerTable = []
        self.setName(threadName)
        self.host = socket.gethostname()

        if len(sys.argv) == 4:
            self.ringIp = sys.argv[1]
            self.mBits  = sys.argv[2]
            self.number  = sys.argv[3]

            serverInfo = self.chord.doJoin(self.ringIp, ServerInfo(serverID=self.number, source=self.host))

            self.next = serverInfo.next
            self.back = serverInfo.back
        elif len(sys.argv) == 3:
            self.mBits  = sys.argv[1]
            self.number  = sys.argv[2]
        else:
            print("Arguments Invalid ([IP] M N)")
            sys.exit(1)


    def run(self):
        server = grpc_server(futures.ThreadPoolExecutor(max_workers=10))
        add_P2PServicer_to_server(self.chord, server)
        server.add_insecure_port("0.0.0.0:" + self.port)
        server.start()
        try:
            print("I'm listen in chord!")
            while not self.stopEvent.isSet():
                sleep(timeToSleep)
        except KeyboardInterrupt:
            server.stop(0)
        self.stopEvent.clear()
        self.stopFinish.set()