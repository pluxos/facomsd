import os
import configparser

from asyncService import AsyncService
from time import sleep


CONFIG = configparser.ConfigParser()
CONFIG.read(os.path.dirname(__file__) + '/../../config.py')

class Build_finger_table(AsyncService):

    def __init__(self, node):
        AsyncService.__init__(self)
        self.node = node
        self.wait_time = CONFIG.getint('p2p', 'SLEEP_BUILD_FT')


    def run(self):
        while not self.stopEvent.isSet():
            sleep(10)
            print("Building finger table...")
            self.node.chord.fill_finger_table()
            print(self.node.fingerTable)

        self.stopEvent.clear()
        self.stopFinish.set()
        print("Exiting build finger table...")