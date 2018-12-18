from __future__ import absolute_import
from sd_work_pb2_grpc import ServerServicer
from sd_work_pb2 import Id, Data, ServerResponse

from grpc import StatusCode
from Queue import Queue

class Server(ServerServicer):

    def __init__(self, requests):
        self.requests = requests

    def create(self, request, context):
        return self.enqueue_request(request, u'CREATE', context)

    def read(self, request, context):
        return self.enqueue_request(request, u'READ', context)

    def update(self, request, context):
        return self.enqueue_request(request, u'UPDATE', context)

    def delete(self, request, context):
        return self.enqueue_request(request, u'DELETE', context)

    def enqueue_request(self, request, _type, context):
        pipe = Queue()
        self.requests.put((pipe, (_type, request)))
        response = pipe.get()
        del pipe

        print 'Response to: ' + str(request) + '\n is: ' + str(response)
        if type(response) != ServerResponse:
            context.set_code(response)
            response = ServerResponse(message=response.name)
        else:
            context.set_code(StatusCode.OK)
        return response
