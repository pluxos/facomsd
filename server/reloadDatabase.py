import configparser

CONFIG = configparser.ConfigParser()
CONFIG.read('../config.py')


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
                self.requests.put((command, None))
            print("database reloading..")


