import configparser
import os
from grpc import insecure_channel
from server_side_pb2 import ServerInfo, ServerID
from server_side_pb2_grpc import P2PServicer, P2PStub
from sd_work_pb2_grpc import ServerStub


CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../../config.py')

class Chord(P2PServicer):

    def __init__(self, node):
        self.node = node
        self.port = str(CONFIG.getint('p2p', 'PORT'))
        self.client_port = str(CONFIG.getint('all', 'PORT'))
        self.thisHost = self.node.host

    def getNeighbors(self, request, context):
        ft = self.node.fingerTable
        this = ServerID(host=self.thisHost, id=self.node.id)
        if len(self.node.fingerTable) == 0:
            response = ServerInfo(next=this, back=this)

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
            print("forward search neighbor call from: ", request.serverID)
            response = near.getNeighbors(request)

        return response

    def join(self, request, context):
        if len(request.next.host) > 0:
            print("\n\n\nJOIN:", request.next, "\n\n\n")
            next = self.server_id_to_finger_table(request.next)
            if len(self.node.fingerTable) >= 2:
                self.node.fingerTable[1] = next
            else:
                self.node.fingerTable = [None, next]

        if len(request.back.host) > 0:
            print("\n\n\nJOIN:", request.back, "\n\n\n")
            back = self.server_id_to_finger_table(request.back)
            if len(self.node.fingerTable) >= 2:
                self.node.fingerTable[0] = back
            else:
                self.node.fingerTable = [back, None]

        print("My fingerTable Updated: ")

        print("[", end="")
        for i in self.node.fingerTable:
            print(self.node.fingerTable)
            # if i is not None:
            #     print(i[0],i[1], end=" ,")
            # else:
            #     print(i, end=" ,")
        print("]")
        return ServerInfo(serverID=self.node.id, source=self.thisHost)


    def doJoin(self, destiny,  serverInfo):
        channel = insecure_channel(destiny + ":" + self.port)
        this = ServerID(host=self.thisHost, id=self.node.id)
        stub = P2PStub(channel)
        near = stub.getNeighbors(serverInfo)

        print(near)
        next = self.server_id_to_finger_table(near.next)
        next[2].join(ServerInfo(back=this, source=self.node.host, serverID=self.node.id))
        #next = (n.serverID, n.source, stubNext)

        back = self.server_id_to_finger_table(near.back)
        back[2].join(ServerInfo(next=this, source=self.node.host, serverID=self.node.id))
        #back = (b.serverID, b.source, stubBack)

        self.node.fingerTable = [back, next]

        print("My fingerTable: ", self.node.fingerTable)

    def getStub(self, server):
        channel = insecure_channel(server.host)
        stub = P2PStub(channel)
        return stub

    def getClientStub(self, server):
        host = server.host.split(':')[0] + ':' + self.client_port
        channel = insecure_channel(host)
        stub = ServerStub(channel)
        return stub

    def server_id_to_finger_table(self, server):
        stub = self.getStub(server)
        clientStub = self.getClientStub(server)
        return (server.id, server.host, stub, clientStub)

    def doExit(self, ServerInfo):
        pass

    def search_by_id(self, target, client_interface=False):
        for i in range(1, len(self.node.fingerTable) + 1):
            if self.node.fingerTable[-i][0] < target:
                print("Search_by_id found -i : ",self.node.fingerTable[-i][0])
                if client_interface:
                    return self.node.fingerTable[-i][3]
                else:
                    return self.node.fingerTable[-i][2]
        print("Search_by_id found: ",self.node.fingerTable[1][0])
        if client_interface:
            return self.node.fingerTable[1][3]
        else:
            return self.node.fingerTable[1][2]
