from asyncService import AsyncService
from queue import Empty


class Logger(AsyncService):

    def __init__(self, toLog, threadName):
        AsyncService.__init__(self)
        self.toLog = toLog
        self.filename = 'log.requests'
        self.setName(threadName)

    def run(self):

        while not self.stopEvent.isSet():
            try:
                request = self.toLog.get(True, 1)
                with open(self.filename, 'a') as logFile:
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
