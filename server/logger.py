import configparser
import os
from asyncService import AsyncService
from queue import Empty

CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')

class Logger(AsyncService):

    def __init__(self, toLog, threadName, reloadDB):
        AsyncService.__init__(self)
        self.toLog = toLog
        self.setName(threadName)
        self.reloadDB = reloadDB
        self.digitSize = CONFIG.getint('backup', 'digitSize')

    def run(self):

        while not self.stopEvent.isSet():
            try:
                request = self.toLog.get(True, 1)
                version = self.reloadDB.snapshot_version
                a = '0' * (self.digitSize - len(str(version)))
                with open('../logs/log.' + a + str(version - 1), 'a') as logFile:
                    request = self.getRequest(request)
                    logFile.write(request + '\n')
            except Empty:
                continue

        print("Exiting logger")
        self.stopEvent.clear()
        self.stopFinish.set()

    def getRequest(self, request):

        type = request[0].upper()
        if type == "DELETE":
            return 'DELETE ' + request[1].id.decode()
        elif type == "CREATE":
            return 'CREATE ' + request[1].id.decode() + ' ' + request[1].data
        elif type == "UPDATE":
            return 'UPDATE ' + request[1].id.decode() + ' ' + request[1].data
