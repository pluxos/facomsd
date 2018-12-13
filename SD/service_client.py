import os
import time
from uuid import getnode as get_mac

serial = 0


def get_id():
    global serial
    serial += 1
    id = str(serial)
    id += str(os.getpid())
    # id += str(int(time.time()))
    # id += str(get_mac())
    return int(id)
