import configparser
import os
import pickle
from sd_work_pb2 import Id, Data
from asyncService import AsyncService
from time import sleep, time

CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')

class ReloadDatabase(AsyncService):

    def __init__(self, persistence, splitter):
        AsyncService.__init__(self)
        self.filename = CONFIG.get('all', 'FILENAME')
        #self.requests = requests
        self.persistence = persistence
        self.splitter = splitter
        self.time_inter_snap = 10
        self.toPersist = self.splitter.toPersist
        self.toLog = self.splitter.toLog
        self.pauseSplit = self.splitter.pause
        self.snapshot_version = 1
        self.digitSize = CONFIG.getint('backup', 'digitSize')
        self.verifySnapshotVersion()

        self.load()

    def run(self):
        init = time()
        while not self.stopEvent.isSet():
            if (time() - init) > self.time_inter_snap:
                self.saveSnapshot()
                init = time()
            sleep(1)

        self.stopEvent.clear()
        self.stopFinish.set()

    def saveSnapshot(self):
        print("Make the snapshot!")
        self.pauseSplit.set()
        while not self.toPersist.empty() or not self.toLog.empty():
            print("Wait to process snapshot")
            sleep(0.1)

        snapshot = pickle.dumps(self.persistence.data)

        a = '0' * (self.digitSize - len(str(self.snapshot_version)))

        with open('./snapshots/snap.' + a + str(self.snapshot_version), 'wb') as file:
            file.write(snapshot)

        with open('./logs/log.' + a + str(self.snapshot_version), 'w'):
            pass

        self.snapshot_version += 1

        self.clearOldBackups()
        self.pauseSplit.clear()
        print("Snapshot are done!")

    def load(self):
        try:
            a = '0' * (self.digitSize - len(str(self.snapshot_version)))
            with open('snapshots/snap.' + a + str(self.snapshot_version-1), 'rb') as f:
                self.persistence.data = pickle.loads(f.read())
        except FileNotFoundError:
            pass
        self.loadFromLog()

    def loadFromLog(self):
        a = '0' * (self.digitSize - len(str(self.snapshot_version)))
        try:
            with open('logs/log.' + a + str(self.snapshot_version-1), 'r') as file:
                print("database reloading..")
                while True:
                    command = file.readline()
                    if len(command) == 0:
                        break
                    command = command.split()
                    if command[0] == "DELETE":
                        request = Id(id=command[1].encode())
                        self.persistence.delete(request, None)
                    elif command[0] == "UPDATE" or command[0] == 'CREATE':
                        request = Data(id=command[1].encode(), data=command[2])
                        if command[0] == "UPDATE":
                            self.persistence.update(request, None)
                        else:
                            self.persistence.create(request, None)
                    else:
                        print("command fail, continue..")
                        continue
        except FileNotFoundError:
            pass
                #self.requests.put((None, (command[0], request)))
        self.persistence.isLoaded.set()

    def verifySnapshotVersion(self):
        snaps = os.listdir('snapshots')

        if len(snaps) > 0:
            snaps.sort()
            lastSnap = snaps[-1]
            lastSnapID = int(lastSnap.split('snap.')[1])
            self.snapshot_version = lastSnapID + 1

    def clearOldBackups(self):
        self.clearFiles('snapshots/')
        self.clearFiles('logs/')


    def clearFiles(self, dir):
        files = os.listdir(dir)
        if len(files) > 3:
            files.sort(reverse=True)
            for f in files[3:]:
                os.remove(dir + f)