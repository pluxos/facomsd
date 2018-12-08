from __future__ import with_statement
from __future__ import absolute_import
import ConfigParser
import os
from asyncService import AsyncService
from Queue import Empty
from io import open

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../config.py')

class Logger(AsyncService):

    def __init__(self, toLog, threadName, reloadDB):
        AsyncService.__init__(self)
        self.toLog = toLog
        self.setName(threadName)
        self.reloadDB = reloadDB
        self.digitSize = CONFIG.getint(u'backup', u'digitSize')

    def run(self):

        while not self.stopEvent.isSet():
            try:
                request = self.toLog.get(True, 2)
                version = self.reloadDB.snapshot_version
                a = u'0' * (self.digitSize - len(unicode(version)))
                with open(u'../logs/log.' + a + unicode(version - 1), u'a') as logFile:
                    request = self.getRequest(request)
                    logFile.write(request + u'\n')
            except Empty:
                continue

        print u"Exiting logger"
        self.stopEvent.clear()
        self.stopFinish.set()

    def getRequest(self, request):

        type = request[0].upper()
        if type == u"DELETE":
            return u'DELETE ' + request[1].id.decode()
        elif type == u"CREATE":
            return u'CREATE ' + request[1].id.decode() + u' ' + request[1].data
        elif type == u"UPDATE":
            return u'UPDATE ' + request[1].id.decode() + u' ' + request[1].data
