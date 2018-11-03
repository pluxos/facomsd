import sys
sys.path.append('../grpcDefinitions')

from grpc import insecure_channel
from sd_work_pb2_grpc import ServerStub
from sd_work_pb2 import Id, Data

import configparser

import os
CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')

idNotInteger = "ID not is Integer!"
invalidFormatCreate = "invalid arguments! (CREATE ID VALUE)"
invalidFormatRead = "invalid arguments! (READ ID)"
invalidFormatUpdate = "invalid arguments! (UPDATE ID VALUE)"
invalidFormatDelete = "invalid arguments! (DELETE ID)"

class ConnectionWithGRPC:

    def __init__(self):
        if len(sys.argv) == 2:
            self.host = sys.argv[1]
        else:
            print("insert server ip to connect in arguments! (python3 __init__.py  IP)")
            sys.exit(1)
        self._channel = insecure_channel(self.host + ":" + str(CONFIG.getint('all', 'PORT')))
        self.stub = ServerStub(self._channel)
        print("connection stabilish" , self._channel)
        print("connection stabilish" , self.stub)

    def read(self, id):
        id = self.verify_one_arg(id, invalidFormatRead)
        result = self.stub.read.future(id)
        #print(result.data)
        result.add_done_callback(response_handler)

    def create(self, args):
        data = self.verify_two_arg(args, invalidFormatCreate)
        result = self.stub.create.future(data)
        result.add_done_callback(response_handler)

    def update(self, args):
        data = self.verify_two_arg(args, invalidFormatUpdate)
        result = self.stub.update.future(data)
        result.add_done_callback(response_handler)

    def delete(self, id):
        id = self.verify_one_arg(id, invalidFormatDelete)
        result = self.stub.delete.future(id)
        result.add_done_callback(response_handler)

    def verify_one_arg(self, id, invalidFormat):
        if len(id) > 1:
            raise Exception(invalidFormat)
        try:
            int(id[0])
            id = id[0].encode()
        except Exception:
            raise Exception(idNotInteger)
        return Id(id=id)

    def verify_two_arg(self, args, invalidFormat):
        if len(args) != 2:
            raise Exception(invalidFormat)
        try:
            int(args[0])
            args[0] = args[0].encode()
        except Exception:
            raise Exception("ID not is Integer!")

        return Data(id=args[0], data=args[1])


def response_handler(response):
    try:
        r = response.result()
        if r.data is not None:
            print("Data: ", r.data)
            print("Message: ", r.message)
        else:
            print("Code: ", response.code())
    except Exception as e:
        print("Data :", e.code().name)

