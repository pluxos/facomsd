from asyncService import AsyncService
from queue import Empty


class Logger(AsyncService):

    def __init__(self, toLog):
        AsyncService.__init__(self)
        self.toLog = toLog
        self.filename = 'log.requests'

    def run(self):

        while not self.stopEvent.isSet():
            try:
                request = self.toLog.get(True, 1)
                with open(self.filename, 'a') as logFile:
                    logFile.write(request + '\n')
            except Empty:
                continue

        print("Exiting logger")
        self.stopEvent.clear()
        self.logFile.close()