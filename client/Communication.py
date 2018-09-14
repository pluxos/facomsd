import threading
import os
from datetime import datetime
from Connection import Connection


class Communication(threading.Thread, Connection):

    def __init__(self):
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()

    def run(self):
        print("Starting Thread of Communication...")

        while not self.stopRequest.isSet():
            # Do nothing
            pass
        print("Stop Thread of Communication in ", datetime.now())

    def join(self, timeout=None):
        print("Request Stop in ", datetime.now())
        super(Communication, self).join(timeout)
        self.stopRequest.set()
