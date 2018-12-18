from __future__ import absolute_import
from asyncService import AsyncService
from Queue import Empty
from grpc import StatusCode, RpcError
import ConfigParser
import os

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../config.py')

class Redirector(AsyncService):


    def __init__(self, node, toRedirect, threadName,):
        AsyncService.__init__(self)
        self.setName(threadName)
        self.toRedirect = toRedirect
        self.node = node
        self.chord = node.chord
        self.timeout_request = CONFIG.getint(u'p2p', u'TIMEOUT_REQUEST')



    def run(self):
        while not self.stopEvent.isSet():

            try:
                connection, request = self.toRedirect.get(True, 2)

                id = int(request[1].id.decode())

                if id < 0 or id >= (1 << self.node.mBits):
                    self.return_not_found(connection)
                    continue

                responsible_server_id = self.node.fromID(id)

                print u"Responsable for", id, u"is the node", responsible_server_id

                clients = self.chord.search_by_id(responsible_server_id, True)

                request_type = request[0]
                result = None
                for stub in clients:
                    try:
                        if request_type == u'READ':
                            result = stub.read(request[1],   timeout=self.timeout_request)
                        elif request_type == u'CREATE':
                            result = stub.create(request[1], timeout=self.timeout_request)
                        elif request_type == u'UPDATE':
                            result = stub.update(request[1], timeout=self.timeout_request)
                        else:
                            result = stub.delete(request[1], timeout=self.timeout_request)
                        break
                    except RpcError as e:
                        if e.code() != StatusCode.DEADLINE_EXCEEDED and e.code() != StatusCode.UNAVAILABLE:
                            result = e.code()
                            break
                        print 'retrying redirect the request: ' + str(request)

                if result is None:
                    result = StatusCode.INTERNAL
                    response_request(connection, result)
                    raise Exception('Fault in redirect to responsable node, cannot connect with the node!')

                response_request(connection, result)
                # result.add_done_callback(get_response_handler(connection))

            except Empty:
                continue

        print u"Exiting Redirector"
        self.stopEvent.clear()
        self.stopFinish.set()

    def return_not_found(self, connection):
        connection.put(StatusCode.OUT_OF_RANGE)


def response_request(connection, response):
    connection.put(response)

def get_response_handler(_connection):
    def response_handler(response, connection=_connection):
        try:
            r = response.result()
            connection.put(r)
        except Exception, e:
            connection.put(e.code())
    return response_handler