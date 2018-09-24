from queue import Queue

from persistent import Persistent
from listener   import Listener
from splitter   import Splitter
from logger     import Logger

from time import sleep

import signal


def stop(sig, frame):
    global run
    logger.join()
    splitter.join()
    listener.join()
    run = False

run = True
signal.signal(signal.SIGINT, stop)


requests = Queue()
waitLog = Queue()
waitPersist = Queue()


splitter = Splitter(requests, waitLog, waitPersist)
splitter.start()

logger = Logger(waitLog)
logger.start()

persistent = Persistent(waitPersist)
persistent.start()

# Start Listerner in new Thread
listener = Listener(requests)
listener.start()

while run:
    pass

stop(None, None)