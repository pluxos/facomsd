from __future__ import absolute_import
import ConfigParser
import os
from grpc import insecure_channel
from server_side_pb2 import ServerInfo, ServerID, FingerTable
from server_side_pb2_grpc import P2PServicer, P2PStub
from sd_work_pb2_grpc import ServerStub
from math import log, ceil, floor

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../../config.py')


class Cluster(P2PServicer):

    def __init__(self, node):
        self.node = node
        self.port = unicode(CONFIG.getint(u'p2p', u'PORT'))
        self.client_port = unicode(CONFIG.getint(u'all', u'PORT'))
        self.thisHost = self.node.host

    def notify_cluster(self, request, context):
        pass