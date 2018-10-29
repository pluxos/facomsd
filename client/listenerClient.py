import threading
from datetime import datetime
from time import sleep
from connection_with_grpc import ConnectionWithGRPC
from queue import Empty


class Listener(threading.Thread, ConnectionWithGRPC):

    def __init__(self, toProcess, threadName):
        ConnectionWithGRPC.__init__(self)
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()
        self.setName(threadName)
        self.daemon = True
        self.toProcess = toProcess

    def run(self):
        print("Starting Thread of Communication...")

        while not self.stopRequest.isSet():
            try:
                command = self.toProcess.get(True, 1)
                if command[0].upper() == "READ":
                    self.read(command[1:])
                elif command[0].upper() == "CREATE":
                    self.create(command[1:])
                elif command[0].upper() == "UPDATE":
                    self.update(command[1:])
                elif command[0].upper() == "DELETE":
                    self.delete(command[1:])
            except Empty:
                continue
            except Exception as e:
                print("Exception: ", e)

        print("Stop Thread of Communication in ", datetime.now())

    def stopListen(self, timeout=None):
        print("Request Stop in ", datetime.now())
        if timeout is None:
            return
        sleep(timeout)
        self.stopRequest.set()
