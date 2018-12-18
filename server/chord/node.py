from __future__ import division
from __future__ import absolute_import
import ConfigParser
import os
import socket
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
        self.get_arguments()

        self.port = unicode(CONFIG.getint(u'p2p', u'PORT'))
        self.cluster_port = unicode(CONFIG.getint(u'cluster', u'PORT'))
        self.fingerTable = []
        self.cluster_table = []
        self.is_cluster_builded = True
        self.setName(threadName)
        self.ip = socket.gethostbyname(socket.getfqdn())
        self.host = self.get_hosts_in_cluster() #self.ip + u":" + self.port

        self.chord = Chord(self)
        self.cluster = Cluster(self)

        self.build_finger_table = Build_finger_table(self)

        self.start_services()

        self.build_finger_table.build_cluster_stubs()
        print self.ip_cluster
        print 'My replica IP is %s' %(self.ip_replica)


        if self.ringIp is not None and len(self.ringIp) > 0:
            self.chord.doJoin(self.ringIp, ServerInfo(serverID=self.id, source=self.host))

        print u"My range is: [", self.toID(self.id-1),u",", self.toID(self.id),u")"

    def get_hosts_in_cluster(self):

        hosts = []

        for ip in self.ip_cluster:
            hosts.append(ip + ':' + self.port)

        return hosts


    def get_arguments(self):

        parse = argparse.ArgumentParser()
        parse.add_argument('--ring', dest='ringIp', default=None)
        parse.add_argument('-m', dest='mBits')
        parse.add_argument('-n', dest='number')
        parse.add_argument('--id', dest='id')
        parse.add_argument('--ip-replica', dest='ip_replica')
        parse.add_argument('--name', dest='name')

        args = parse.parse_args()

        self.ringIp = args.ringIp
        self.mBits = int(args.mBits)
        self.number = int(args.number)
        self.id = int(args.id)
        self.ip_replica = args.ip_replica
        self.name = args.name

        aux = []
        for e in self.ip_replica.split(', '):
            aux.append( e.split(':')[0] )

        self.ip_cluster = aux

    def start_services(self):
        self._server = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_P2PServicer_to_server(self.chord, self._server)
        self._server.add_insecure_port(u"[::]:" + self.port)
        self._server.start()

        self._cluster = grpc_server(futures.ThreadPoolExecutor(max_workers=100))
        add_P2PServicer_to_server(self.cluster, self._cluster)
        self._cluster.add_insecure_port(u"[::]:" + self.cluster_port)
        self._cluster.start()

    def run(self):

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

