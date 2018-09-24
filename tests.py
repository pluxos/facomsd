from __future__ import absolute_import
import sys
import os
import socket
from time import sleep
os.environ['CONFIG_VALUE'] = 'config.py'
sys.path.append('./client')
sys.path.append('./server')


from client import Client
from server import Server


def firstTest():
    os.system("clear")
    print("\n\n ====== CRUD OK TEST ======= \n\n")
    client.command.send("create 1 123".split())
    sleep(1)
    client.command.send("read 1".split())
    sleep(1)
    client.command.send("update 1 12345".split())
    sleep(1)
    client.command.send("read 1".split())
    sleep(1)
    client.command.send("delete 1".split())
    restartServer()

def secondTest():
    os.system("clear")
    print("\n\n ====== CRUD NOK TEST ======= \n\n")
    client.command.send("create 1 123".split())
    sleep(1)
    client.command.send("create 1 123".split())
    sleep(1)
    client.command.send("read 1111".split())
    sleep(1)
    client.command.send("update 1111 12345".split())
    sleep(1)
    client.command.send("create 1111 111".split())
    sleep(1)
    client.command.send("read 1111".split())
    sleep(1)
    client.command.send("delete 1111".split())
    restartServer()


def stateRecovery():

    os.system("clear")
    print("\n\n ====== STATE RECOVERY TEST ======= \n\n")
    for i in range(0,5):
        client.command.send(("create " + str(i) + " 124").split())
        sleep(1)

    global server

    restartServer()


    for i in range(5,10):
        client.command.send(("create " + str(i) + " 124").split())
        sleep(1)
    restartServer()

def executionOrderTest():
    v = 1
    for i in range(1001):
        client.command.send(("create " + str(i) + " " + str(v)).split())
        v += 1
        sleep(0.1)

    client.command.send("read 1000".split())

    #restartServer()


def clearFile():
    with open('log.requests', 'w'):
        pass

def restartServer():
    global server
    #input("Press enter to continue...")
    sleep(7)
    server.join()
    server = Server()
    server.start()


clearFile()
server = Server()
server.start()

client = Client()
client.start()

clearFile()
firstTest()
clearFile()
secondTest()
clearFile()
stateRecovery()
clearFile()
executionOrderTest()
clearFile()
