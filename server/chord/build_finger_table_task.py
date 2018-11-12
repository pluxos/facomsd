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
            sleep(self.wait_time)
            # print("Building finger table...")
            self.node.chord.fill_finger_table()
            self.print_table()
            self.wait_time += 5

        self.stopEvent.clear()
        self.stopFinish.set()
        print("Exiting build finger table...")

    def print_table(self):

        ft = self.node.fingerTable
        if len(ft) > 1:
            print("\n")
            print("My predecessor is ", ft[0][0])
            print("My successor is   ", ft[1][0])

            if len(ft) > 2:
                print("My finger table is [",end="")
                for i in range(1,len(ft)):
                    print(ft[i][0],end="")
                    if i != len(ft)-1:
                        print(', ', end="")
                print("]")
            else:
                print("Finger Table empty")
            print("\n")