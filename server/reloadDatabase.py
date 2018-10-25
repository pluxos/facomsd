import configparser
import os

from sd_work_pb2 import Id, Data

CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../config.py')

class ReloadDatabase:

    def __init__(self, requests):
        self.filename = CONFIG.get('all', 'FILENAME')
        self.file = open(self.filename, 'r')
        self.requests = requests

    def load(self):
        if self.file is not None:
            while True:
                command = self.file.readline()
                if len(command) == 0:
                    self.file.close()
                    self.file = None
                    break
                command = command.split()
                if command[0] == "READ" or command[0] == "DELETE":
                    request = Id(id=command[1].encode())
                elif command[0] == "UPDATE" or command[0] == 'CREATE':
                    request = Data(id=command[1].encode(), data=command[2])
                else:
                    print("command fail, continue..")
                    continue

                self.requests.put((None, (command[0], request)))
            print("database reloading..")


