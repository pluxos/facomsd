from __future__ import absolute_import
from concoord.clientproxy import ClientProxy
from Queue import Queue

class Enqueue:

    def __init__(self, bootstrap):
        self.proxy = ClientProxy(bootstrap, token='None')

    def __concoordinit__(self, requests=None):
        return self.proxy.invoke_command('__concoordinit__', requests)

    def enqueue_request(self, request, _type, context):
        return self.proxy.invoke_command('enqueue_request', request, _type, context)