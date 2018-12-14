from __future__ import with_statement
from __future__ import absolute_import

import ConfigParser
import os
import pickle
from sd_work_pb2 import Id, Data
from asyncService import AsyncService
from time import sleep, time
from io import open

CONFIG = ConfigParser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + u'/../config.py')

LOG_PATH = u'../logs'
SNAP_PATH = u'../snapshots'

class ReloadDatabase(AsyncService):

    def __init__(self, persistence, splitter):
        AsyncService.__init__(self)
        self.filename = CONFIG.get(u'all', u'FILENAME')
        #self.requests = requests
        self.persistence = persistence
        self.splitter = splitter
        self.time_inter_snap = 10
        self.toPersist = self.splitter.toPersist
        self.toLog = self.splitter.toLog
        self.pauseSplit = self.splitter.pause
        self.snapshot_version = 1
        self.digitSize = CONFIG.getint(u'backup', u'digitSize')
        self.verifySnapshotVersion()

        self.createIfNotFound(LOG_PATH)
        self.createIfNotFound(SNAP_PATH)

        self.load()

    def run(self):
        pass
        # init = time()
        # while not self.stopEvent.isSet():
        #     if (time() - init) > self.time_inter_snap:
        #         self.saveSnapshot()
        #         init = time()
        #     sleep(1)
        #
        # self.stopEvent.clear()
        # self.stopFinish.set()

    def createIfNotFound(self, dir):
        try:
            os.listdir(dir)
        except OSError:
            os.mkdir(dir)

    def saveSnapshot(self):
        # print("Make the snapshot!")
        self.pauseSplit.set()
        while not self.toPersist.empty() or not self.toLog.empty():
            print u"Wait to process snapshot"
            sleep(0.1)

        snapshot = pickle.dumps(self.persistence.data, protocol=2)

        a = u'0' * (self.digitSize - len(unicode(self.snapshot_version)))

        with open(SNAP_PATH+u'/snap.' + a + unicode(self.snapshot_version), u'wb') as file:
            file.write(snapshot)

        with open(LOG_PATH + u'/log.' + a + unicode(self.snapshot_version), u'w'):
            pass

        self.snapshot_version += 1

        self.clearOldBackups()
        self.pauseSplit.clear()
        # print("Snapshot are done!")

    def load(self):
        try:
            a = u'0' * (self.digitSize - len(unicode(self.snapshot_version)))
            with open(SNAP_PATH + u'/snap.' + a + unicode(self.snapshot_version-1), u'rb') as f:
                self.persistence.data = pickle.loads(f.read())
        except IOError:
            pass
        self.loadFromLog()

    def loadFromLog(self):
        a = u'0' * (self.digitSize - len(unicode(self.snapshot_version)))
        try:
            with open(LOG_PATH + u'/log.' + a + unicode(self.snapshot_version-1), u'r') as file:
                print u"database reloading.."
                while True:
                    command = file.readline()
                    if len(command) == 0:
                        break
                    command = command.split()
                    if command[0] == u"DELETE":
                        request = Id(id=command[1].encode())
                        self.persistence.delete(request, None)
                    elif command[0] == u"UPDATE" or command[0] == u'CREATE':
                        request = Data(id=command[1].encode(), data=command[2])
                        if command[0] == u"UPDATE":
                            self.persistence.update(request, None)
                        else:
                            self.persistence.create(request, None)
                    else:
                        print u"command fail, continue.."
                        continue
        except IOError:
            pass
                #self.requests.put((None, (command[0], request)))
        self.persistence.isLoaded.set()

    def verifySnapshotVersion(self):
        self.createIfNotFound(SNAP_PATH)

        snaps = os.listdir(SNAP_PATH)

        if len(snaps) > 0:
            snaps.sort()
            lastSnap = snaps[-1]
            lastSnapID = int(lastSnap.split(u'snap.')[1])
            self.snapshot_version = lastSnapID + 1

    def clearOldBackups(self):
        self.clearFiles(SNAP_PATH)
        self.clearFiles(LOG_PATH)


    def clearFiles(self, dir):
        self.createIfNotFound(dir)
        files = os.listdir(dir)
        if len(files) > 3:
            files.sort(reverse=True)
            for f in files[3:]:
                os.remove(dir + u'/' + f)