from sd_work_pb2_grpc import ServerServicer
from sd_work_pb2 import Id, Data, ServerResponse

from grpc import StatusCode
from queue import Queue

class Server(ServerServicer):

    def __init__(self, requests):
        self.requests = requests

    def create(self, request, context):
        return self.enqueue_request(request, 'CREATE', context)

    def read(self, request, context):
        return self.enqueue_request(request, 'READ', context)

    def update(self, request, context):
        return self.enqueue_request(request, 'UPDATE', context)

    def delete(self, request, context):
        return self.enqueue_request(request, 'DELETE', context)

    def enqueue_request(self, request, _type, context):
        pipe = Queue()
        self.requests.put((pipe, (_type, request)))
        response = pipe.get()
        del pipe

        if type(response) != ServerResponse:
            context.set_code(response)
            response = ServerResponse(message=response.name)
        else:
            context.set_code(StatusCode.OK)
        return response
