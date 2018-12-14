from __future__ import absolute_import
from time import sleep

import threading


class AsyncService(threading.Thread):

    def __init__(self):
        threading.Thread.__init__(self)
        self.stopEvent = threading.Event()
        self.stopFinish = threading.Event()

    def join(self, timeout=None):
        self.stopEvent.set()
        while not self.stopFinish.isSet():
            sleep(2)
            pass
