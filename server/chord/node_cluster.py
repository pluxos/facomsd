from __future__ import absolute_import
import ConfigParser
import os
from time import time
from datetime import datetime
from grpc import insecure_channel
from server_side_pb2 import ServerInfo, ServerID, FingerTable, Void
from server_side_pb2_grpc import P2PServicer, P2PStub
from sd_work_pb2_grpc import ServerStub
from math import log, ceil, floor

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../../config.py')


class Cluster(P2PServicer):

    def __init__(self, node):
        self.node = node
        self.port = unicode(CONFIG.getint(u'cluster', u'PORT'))
        self.client_port = unicode(CONFIG.getint(u'all', u'PORT'))
        self.thisHost = self.node.host

    def notify_cluster(self, request, context):

        position = 1
        for i in request.table:
            if i.host is None or len(i.host) == 0:
                entry = None
            else:
                entry = self.node.chord.server_id_to_finger_table(i)

            if len(self.node.fingerTable) > position:
                self.node.fingerTable[position] = entry
            else:
                self.node.fingerTable.append(entry)
            position += 1
        self.node.build_finger_table.print_table()
        return Void()

    def finger_table_to_server_id(self, entry):
        return ServerID(host=entry[1], id=entry[0])

    def build_finger_to_cluster(self):
        this = ServerID(host=self.thisHost, id=self.node.id)
        response = FingerTable(source=this)

        for e in self.node.fingerTable:
            if e is not None:
                entry = ServerID(host=e[1], id=int(e[0]))
            else:
                entry = ServerID()
            response.table.extend([entry])
        self.node.build_finger_table.print_table()
        while True:
            try:
                self.node.build_finger_table.build_cluster_stubs()
                for stub in self.node.cluster_table:
                    stub.notify_cluster(response)
                print '\n\nsuccess in notify!!!\n\n'
                self.node.build_finger_table.print_table()
                break
            except Exception as e:
                # print 'Fault in notify_cluster' + str(e)
                print datetime.fromtimestamp(time()).strftime('%Y-%m-%d %H:%M:%S') + 'Fault in notify_cluster' + str(e)




