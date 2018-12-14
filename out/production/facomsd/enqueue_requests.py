from __future__ import absolute_import
from Queue import Queue
import pickle

class Enqueue:

    def __init__(self, requests=None):
        self.requests = pickle.loads(requests)

    def enqueue_request(self, request, _type, context):
        pipe = Queue()
        if self.requests is not None:
            self.requests.put((pipe, (_type, request)))
        else:
            raise Exception('requests not initialized!')
        response = pipe.get()

        return response