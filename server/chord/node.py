import sys
import configparser
import os
import socket
from concurrent import futures
from time import sleep
from grpc import server as grpc_server

from chord_communication import Chord
from server_side_pb2 import ServerInfo
from server_side_pb2_grpc import add_P2PServicer_to_server
from asyncService import AsyncService

CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../../config.py')

timeToSleep = 2

class Node(AsyncService):

    def __init__(self, threadName):
        AsyncService.__init__(self)

        self.port = str(CONFIG.getint('p2p', 'PORT'))
        self.fingerTable = []
        self.setName(threadName)
        self.host = socket.gethostbyname(socket.getfqdn()) + ":" + self.port

        self.chord = Chord(self)

        if len(sys.argv) == 5:
            self.ringIp = sys.argv[1].strip()
            self.mBits  = sys.argv[2].strip()
            self.number  = int(sys.argv[3])
            self.id     = int(sys.argv[4])

            self.chord.doJoin(self.ringIp, ServerInfo(serverID=self.id, source=self.host))

        elif len(sys.argv) == 4:
            self.mBits  = sys.argv[1]
            self.number = sys.argv[2]
            self.id     = int(sys.argv[3])
        else:
            print("Arguments Invalid ([IP] M N ID) '0 < ID <= N'")
            sys.exit(1)

        # print(self.__dict__)


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