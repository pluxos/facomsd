import threading
from connection_with_grpc import ConnectionWithGRPC
import socket


menu = """

commands available:
    read ID
    create ID VALUE
    update ID NEW_VALUE
    delete ID
    exit
    
"""


class Commands(threading.Thread):

    def __init__(self, toProcess, threadName):
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()
        self.setName('commandsThread')
        self.stdinWait = True
        self.setName(threadName)
        self.toProcess = toProcess

    def run(self):
        print("Starting Thread of Commands...")

        while not self.stopRequest.isSet():
            if self.stdinWait:
                try:
                    msg = input(menu).split()
                    if not len(msg):
                        print("Choose one command!")
                        continue
                    if msg[0] == "exit":
                        break
                    else:
                        self.send(msg)
                # except socket.error:
                #     self.reconnect()
                except Exception as error:
                    print("Exception: ", error)
                    continue

    def stop(self):
        self.stopRequest.set()

    def send(self, msg):
        c = msg[0].upper()
        if c == "READ" or c == "CREATE" or c == "UPDATE" or c == "DELETE":
            self.toProcess.put(msg)
        elif msg[0].upper() == "EXIT":
            import sys
            sys.exit()
        else:
            print("Invalid Command!" +  " ---> ", msg)

