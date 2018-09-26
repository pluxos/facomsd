from __future__ import absolute_import
import sys
import os
import threading
import socket
from time import sleep
os.environ['CONFIG_VALUE'] = 'config.py'
sys.path.append('./client')
sys.path.append('./server')


from client import Client
from server import Server, asyncService


def firstTest(client):

    #os.system("clear")
    print("\n\n ====== CRUD OK TEST ======= \n\n")
    client.command.send("create 1 123".split())
    client.command.send("read 1".split())
    client.command.send("update 1 12345".split())
    client.command.send("read 1".split())
    client.command.send("delete 1".split())
    sleep(5)

def secondTest(client):
    #os.system("clear")
    print("\n\n ====== CRUD NOK TEST ======= \n\n")
    client.command.send("create 1 123".split())
    client.command.send("create 1 123".split())
    client.command.send("read 1111".split())
    client.command.send("update 1111 12345".split())
    client.command.send("create 1111 111".split())
    client.command.send("read 1111".split())
    client.command.send("delete 1111".split())
    sleep(5)

def stateRecovery(client):

    #os.system("clear")
    print("\n\n ====== STATE RECOVERY TEST ======= \n\n")
    for i in range(0,5):
        client.command.send(("create " + str(i) + " 124").split())

    restartServer()

    for i in range(5,10):
        client.command.send(("create " + str(i) + " 124").split())

    for i in range(0,10):
        client.command.send(("read " + str(i)).split())

    sleep(5)

def executionOrderTest(client):

    print("\n\n ====== EXECUTION ORDER TEST ======= \n\n")

    v = 1
    for i in range(1001):
        client.command.send(("create " + str(i) + " " + str(v)).split())
        v += 1

    client.command.send("read 1000".split())

    waitServer()
    sleep(5)

def clearFile():
    with open('log.requests', 'w'):
        pass

def restartServer():
    global server
    sleep(5)
    server.join()
    server = Server()
    server.start()

def waitServer():
    while not server.waitPersist.empty() or \
            not server.requests.empty() or \
            not server.waitLog.empty():
        sleep(1)

class ThreadOut:

    def __init__(self, stdout):
        self.fhs = {}
        self.stdout = stdout

    def write(self, value):
        # f = self.fhs.get(
        #         threading.current_thread(),
        #         open('logs/' + str(threading.current_thread().name), 'w')
        #     )
        with open('logs/' + str(threading.current_thread().name) + '.txt', 'a') as f:
            f.write(value)
        # self.fhs[threading.current_thread()] = f
        self.stdout.write(value)


class TestRun(threading.Thread):

    def __init__(self, name, isConcurrence=False):
        threading.Thread.__init__(self)
        self.setName(name)
        self.client = Client(name)
        self.client.command.stdinWait = False
        self.client.start()
        self.isConcurrence = isConcurrence

    def run(self):
        clearFile()
        firstTest(self.client)
        clearFile()
        secondTest(self.client)
        clearFile()
        if not self.isConcurrence:
            stateRecovery(self.client)
            clearFile()
        executionOrderTest(self.client)
        clearFile()
        print("JOIN CLIENT!!")
        input("please wait create object with id 1000\npress enter to continue...")
        sleep(5)


sys.stdout = ThreadOut(sys.stdout)

clearFile()
server = Server()
server.daemon = True
server.start()


test = TestRun('clientUnico')
test.start()
test.join()


threads = []
print("\n\n ====== CONCURRENCE ======= \n\n")
for i in range(10):
    t = TestRun('client-' + str(i), True)
    t.start()
    threads.append(t)

for t in threads:
    t.join()



for i in range(10, 0):
    print('wait for end tests... ' + str(i))
    sleep(1)


