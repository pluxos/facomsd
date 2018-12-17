from __future__ import division
from __future__ import absolute_import
import sys
import ConfigParser
import os
import socket
import sched
from time import time
from datetime import datetime
from concurrent import futures
from time import sleep, time
from math import ceil, floor
from grpc import server as grpc_server

from chord_communication import Chord
from node_cluster import Cluster
from server_side_pb2 import ServerInfo
from server_side_pb2_grpc import add_P2PServicer_to_server
from asyncService import AsyncService
from build_finger_table_task import Build_finger_table
import argparse


CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../../config.py')

timeToSleep = 2

class Node(AsyncService):

    def __init__(self, threadName):
        AsyncService.__init__(self)

        self.port = unicode(CONFIG.getint(u'p2p', u'PORT'))
        self.cluster_port = unicode(CONFIG.getint(u'cluster', u'PORT'))
        self.fingerTable = []
        self.cluster_table = []
        self.is_cluster_builded = True
        self.setName(threadName)
        self.ip = socket.gethostbyname(socket.getfqdn())
        self.host = self.ip + u":" + self.port

        self.chord = Chord(self)
        self.cluster = Cluster(self)

        self.build_finger_table = Build_finger_table(self)

        cluster = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_P2PServicer_to_server(self.cluster, cluster)
        cluster.add_insecure_port(self.ip + u":" + self.cluster_port)
        cluster.start()

        print datetime.fromtimestamp(time()).strftime('%Y-%m-%d %H:%M:%S') + ' --- cluster grpc started!'

        parse = argparse.ArgumentParser()
        parse.add_argument('--ring', dest='ringIp', default=None)
        parse.add_argument('-m', dest='mBits')
        parse.add_argument('-n', dest='number')
        parse.add_argument('--id', dest='id')
        parse.add_argument('--ip-replica', dest='ip_replica')
        parse.add_argument('--name', dest='name')

        args = parse.parse_args()

        self.ringIp = args.ringIp
        self.mBits  = int(args.mBits)
        self.number = int(args.number)
        self.id     = int(args.id)
        self.ip_replica = args.ip_replica
        self.name   = args.name

        aux = []
        for e in self.ip_replica.split(', '):
            aux.append( e.split(':')[0] )

        self.ip_cluster = aux
        self.is_cluster_builded = False
        self.build_finger_table.build_cluster_stubs()
        print self.ip_cluster
        print 'My replica IP is %s' %(self.ip_replica)


        if self.ringIp is not None and len(self.ringIp) > 0:
            self.chord.doJoin(self.ringIp, ServerInfo(serverID=self.id, source=self.host))

        # print sys.argv
        # if len(sys.argv) == 5:
        #     self.ringIp = sys.argv[1].strip()
        #     self.mBits  = int(sys.argv[2].strip())
        #     self.number  = int(sys.argv[3])
        #     self.id     = int(sys.argv[4])
        #
        #
        # elif len(sys.argv) == 4:
        #     self.mBits  = int(sys.argv[1])
        #     self.number = int(sys.argv[2])
        #     self.id     = int(sys.argv[3])
        # else:
        #     print u"Arguments Invalid ([IP] M N ID) '0 < ID <= N'"
        #     sys.exit(1)

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
        except KeyboardInterrupt as e:
            print 'KeyboardInterrupt --> ' + str(e)
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

