from time import sleep

import threading


class AsyncService(threading.Thread):

    def __init__(self):
        threading.Thread.__init__(self)
        self.stopEvent = threading.Event()
        self.stopFinish = threading.Event()
        #self.daemon = True

    def join(self, timeout=None):
        self.stopEvent.set()
        while not self.stopFinish.isSet():
            pass
