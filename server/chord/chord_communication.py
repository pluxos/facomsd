
from grpc import insecure_channel
from server_side_pb2 import ServerInfo
from server_side_pb2_grpc import P2PServicer, P2PStub



class Chord(P2PServicer):

    def join(self, request, context):
        pass

    def exit(self, request, context):
        pass

    def doJoin(self, destiny,  serverInfo):
        channel = insecure_channel(destiny + ":" + str(CONFIG.getint('all', 'PORT')))
        stub = P2PStub(channel)
        return stub.join(serverInfo)

    def doExit(self, ServerInfo):
        pass