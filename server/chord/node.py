from __future__ import division
from __future__ import absolute_import
import sys
import ConfigParser
import os
import socket
import sched
from concurrent import futures
from time import sleep, time
from math import ceil, floor
from grpc import server as grpc_server

from chord_communication import Chord
from server_side_pb2 import ServerInfo
from server_side_pb2_grpc import add_P2PServicer_to_server
from asyncService import AsyncService
from build_finger_table_task import Build_finger_table

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../../config.py')

timeToSleep = 2

class Node(AsyncService):

    def __init__(self, threadName):
        AsyncService.__init__(self)

        self.port = unicode(CONFIG.getint(u'p2p', u'PORT'))
        self.fingerTable = []
        self.setName(threadName)
        self.host = socket.gethostbyname(socket.getfqdn()) + u":" + self.port

        self.chord = Chord(self)

        self.build_finger_table = Build_finger_table(self)
        print sys.argv
        if len(sys.argv) == 5:
            self.ringIp = sys.argv[1].strip()
            self.mBits  = int(sys.argv[2].strip())
            self.number  = int(sys.argv[3])
            self.id     = int(sys.argv[4])

            self.chord.doJoin(self.ringIp, ServerInfo(serverID=self.id, source=self.host))

        elif len(sys.argv) == 4:
            self.mBits  = int(sys.argv[1])
            self.number = int(sys.argv[2])
            self.id     = int(sys.argv[3])
        else:
            print u"Arguments Invalid ([IP] M N ID) '0 < ID <= N'"
            sys.exit(1)

        print u"My range is: [", self.toID(self.id-1),u",", self.toID(self.id),u")"

    def run(self):
        server = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_P2PServicer_to_server(self.chord, server)
        server.add_insecure_port(u"0.0.0.0:" + self.port)
        server.start()
        self.build_finger_table.start()
        try:
            print u"I'm listen in chord!"
            while not self.stopEvent.isSet():
                sleep(timeToSleep)
        except KeyboardInterrupt:
            server.stop(0)
        self.stopEvent.clear()
        self.stopFinish.set()

    def verify_responsibility(self, id):
        print u"Range entre: ",self.toID(self.id-1), id, self.toID(self.id)
        if self.toID(self.id-1) <= id < self.toID(self.id):
            return True
        return False


    def toID(self, i):
        return ceil((i * ((1 << self.mBits) / self.number)))

    def fromID(self, id):
        m2 = (1 << self.mBits)
        return ceil((self.number * id) / m2)
        # return floor(-(((id * self.number)) - (self.number * m2)) / m2)

    def sched_build_finger_table(self):
        s = sched.scheduler(time, sleep)

