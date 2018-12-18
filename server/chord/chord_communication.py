from __future__ import absolute_import
import ConfigParser
import os
from grpc import insecure_channel, StatusCode, RpcError
from server_side_pb2 import ServerInfo, ServerID, FingerTable
from server_side_pb2_grpc import P2PServicer, P2PStub
from sd_work_pb2_grpc import ServerStub
from math import log, ceil, floor

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../../config.py')

class Chord(P2PServicer):

    def __init__(self, node):
        self.node = node
        self.port = unicode(CONFIG.getint(u'p2p', u'PORT'))
        self.client_port = unicode(CONFIG.getint(u'all', u'PORT'))
        self.thisHost = self.node.host
        self.timeout_request = CONFIG.getint(u'p2p', u'TIMEOUT_REQUEST')

    ###################### RPC methods #########################

    def getNeighbors(self, request, context):
        ft = self.node.fingerTable
        this = ServerID(host=self.thisHost, id=self.node.id)
        if len(self.node.fingerTable) == 0:
            response = ServerInfo(next=this, back=this)
        elif request.serverID == ft[1][0]:
            next = ServerID(host=ft[2][1], id=ft[2][0])
            response = ServerInfo(next=next, back=this)
        elif self.node.id < request.serverID < ft[1][0]:
            next = ServerID(host=ft[1][1], id=ft[1][0])
            response = ServerInfo(next=next, back=this)

        elif ft[0][0] < request.serverID < self.node.id:
            back= ServerID(host=ft[0][1], id=ft[0][0])
            response = ServerInfo(next=this, back=back)

        elif ft[1][0] < self.node.id < request.serverID:
            next = ServerID(host=ft[1][1], id=ft[1][0])
            response = ServerInfo(next=next, back=this)

        elif request.serverID < self.node.id < ft[0][0]:
            back = ServerID(host=ft[0][1], id=ft[0][0])
            response = ServerInfo(next=this, back=back)
        else:
            near = self.search_by_id(request.serverID)
            # print("forward search neighbor call from: ", request.serverID)
            for stub in near:
                try:
                    response = stub.getNeighbors(request, timeout=self.timeout_request)
                    break
                except RpcError, e:
                    if e.code() != StatusCode.DEADLINE_EXCEEDED:
                        raise e
                    print 'Retrying redirect getNeighbors()'

        return response

    def join(self, request, context):
        if len(request.next.host) > 0:
            next = self.server_id_to_finger_table(request.next)
            if len(self.node.fingerTable) >= 2:
                self.node.fingerTable[1] = next
            else:
                self.node.fingerTable = [None, next]

        if len(request.back.host) > 0:
            back = self.server_id_to_finger_table(request.back)
            if len(self.node.fingerTable) >= 2:
                self.node.fingerTable[0] = back
            else:
                self.node.fingerTable = [back, None]

        self.node.cluster.build_finger_to_cluster()
        return ServerInfo(serverID=self.node.id, source=self.thisHost)

    def build_finger_table(self, request, context):

        if request.source.id == self.node.id:
            return request

        this = ServerID(host=self.thisHost, id=self.node.id)

        for i in xrange(1, int(floor(log(self.node.number, 2))+1)):
            ith = ((request.source.id + (1 << (i-1))) % self.node.number)
            ith = self.node.number if ith == 0 else ith

            # print("FROM", request.source.id, "Ith - my ID:", ith, " - ", self.node.id)
            if ith == self.node.id:
                request.table.extend([this])
                break

        if len(self.node.fingerTable) > 1:
            nextStub = self.node.fingerTable[1][2]
            attemps = 0
            for stub in nextStub:
                try:
                    result = stub.build_finger_table(request, timeout=self.timeout_request)
                    request = result
                    break
                except RpcError:
                    print 'Retrying build_finger_table!'
                    attemps += 1
            if attemps == len(nextStub):
                raise Exception('Cannot pass to next build_finger_table, cannot connect')

        return request
    
    ##########################################################################

    ########################## Class methods #################################

    def doJoin(self, destiny,  serverInfo):
        channel = insecure_channel(destiny + u":" + self.port)
        this = ServerID(host=self.thisHost, id=self.node.id)
        stub = P2PStub(channel)
        near = stub.getNeighbors(serverInfo)

        next = self.server_id_to_finger_table(near.next)
        attempts = 0
        for stub in next[2]:
            try:
                stub.join(ServerInfo(back=this, source=self.node.host, serverID=self.node.id), timeout=self.timeout_request)
                break
            except RpcError, e:
                if e.code() != StatusCode.DEADLINE_EXCEEDED:
                    raise e
                print 'Retrying execute join...'
                attempts += 1

        if len(next[2]) == attempts:
            raise Exception('Cannot do join in next')


        back = self.server_id_to_finger_table(near.back)

        attempts = 0
        for stub in back[2]:
            try:
                stub.join(ServerInfo(next=this, source=self.node.host, serverID=self.node.id), timeout=self.timeout_request)
                break
            except RpcError, e:
                if e.code() != StatusCode.DEADLINE_EXCEEDED:
                    raise e
                print 'Retrying execute join...'
                attempts += 1

        if len(back[2]) == attempts:
            raise Exception('Cannot do join in back')

        self.node.fingerTable = [back, next]

        self.node.cluster.build_finger_to_cluster()

    def getStub(self, server):
        stub = []
        for h in server.host:
            channel = insecure_channel(h)
            s = P2PStub(channel)
            stub.append(s)
        return stub

    def getClientStub(self, server):
        stub = []
        for h in server.host:
            host = h.split(u':')[0] + u':' + self.client_port
            channel = insecure_channel(host)
            s = ServerStub(channel)
            stub.append(s)
        return stub

    def server_id_to_finger_table(self, server):
        stub = self.getStub(server)
        clientStub = self.getClientStub(server)
        return (server.id, server.host, stub, clientStub)

    def link_sorted(self, ft):
        sorted_ft = []

        for i in xrange(len(ft)):
            sorted_ft.append( (ft[i][0], i) )

        sorted_ft.sort()
        return sorted_ft
    def search_by_id(self, target, client_interface=False):
        ft_sorted = self.link_sorted(self.node.fingerTable)
        ft = self.node.fingerTable
        for i in xrange(1, len(ft_sorted) + 1):
            if ft_sorted[-i][0] <= target:
                # print("Search_by_id found -i : ",self.node.fingerTable[-i][0])
                if client_interface:
                    return ft[ft_sorted[-i][1]][3]
                else:
                    return ft[ft_sorted[-i][1]][2]
        # print("Search_by_id found: ",self.node.fingerTable[1][0])
        if client_interface:
            return ft[1][3]
        else:
            return ft[1][2]

    def fill_finger_table(self):
        this = ServerID(host=self.thisHost, id=self.node.id)
        ft = FingerTable(source=this)

        if len(self.node.fingerTable) > 1:
            attemps = 0
            for stub in self.node.fingerTable[1][2]:
                try:
                    newFt = stub.build_finger_table(ft, timeout=self.timeout_request)
                    break
                except RpcError, e:
                    print 'Retrying init the build_finger_table'
                    attemps += 1

            if attemps == len(self.node.fingerTable[1][2]):
                raise Exception('Fault in init the build finger table, cannot connect to next node!')

            position = 1
            for i in newFt.table:

                entry = self.server_id_to_finger_table(i)

                if len(self.node.fingerTable) > position:
                    self.node.fingerTable[position] = entry
                else:
                    self.node.fingerTable.append(entry)
                position += 1

            self.node.cluster.build_finger_to_cluster()
