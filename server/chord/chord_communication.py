import configparser
import os
from grpc import insecure_channel
from server_side_pb2 import ServerInfo
from server_side_pb2_grpc import P2PServicer, P2PStub



CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../../config.py')

class Chord(P2PServicer):

    def __init__(self, node):
        self.node = node
        self.port = str(CONFIG.getint('p2p', 'PORT'))
        self.thisHost = self.node.host

    def join(self, request, context):
        response = ServerInfo()
        if self.node.next is None or self.node.back is None:
            self.node.next = request.source
            self.node.back = request.source
            print(self.node.__dict__)
            response = ServerInfo(next=self.thisHost, back=self.thisHost)

        print("\n\n\n", context.__dict__, "\n\n\n")
        return response

    def exit(self, request, context):
        pass

    def doJoin(self, destiny,  serverInfo):
        channel = insecure_channel(destiny + ":" + self.port)
        stub = P2PStub(channel)
        return stub.join(serverInfo)

    def doExit(self, ServerInfo):
        pass