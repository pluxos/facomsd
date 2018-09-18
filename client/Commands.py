import threading
from Connection import Connection


menu = """

commands available:
    read ID
    create ID VALUE
    update ID NEW_VALUE
    delete ID
    exit
    
"""


class Commands(threading.Thread, Connection):

    def __init__(self, socket):
        Connection.__init__(self, socket)
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()

    def run(self):
        print("Starting Thread of Commands...")

        while True:
            try:
                msg = input(menu).split()
                if msg[0] == "exit":
                    break
                else:
                    self.send(msg)
            except Exception as error:
                print(error)
                continue

    def send(self, msg):

        if msg[0].upper() == "READ":
            self.read(msg[1:])
        elif msg[0].upper() == "CREATE":
            self.create(msg[1:])
        elif msg[0].upper() == "UPDATE":
            self.update(msg[1:])
        elif msg[0].upper() == "DELETE":
            self.delete(msg[1:])
        else:
            print("Invalid Command!")

    def read(self, id):
        if len(id) > 1:
            raise Exception("invalid arguments! (READ ID)")
        try:
            int(id[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('read ' + id[0]).encode())

    def create(self, args):
        if len(args) != 2:
            raise Exception("invalid arguments! (CREATE ID VALUE)")
        try:
            int(args[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('create ' + args[0] + " " + args[1]).encode())

    def update(self, args):
        if len(args) != 2:
            if len(args) != 2:
                raise Exception("invalid arguments! (UPDATE ID VALUE)")
        try:
            int(args[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('update ' + args[0] + " " + args[1]).encode())

    def delete(self, id):
        print(id)
        if len(id) > 1:
            raise Exception("invalid arguments! (DELETE ID)")
        try:
            int(id[0])
        except Exception:
            raise Exception("ID not is Integer!")
        self.sendRequest(('delete ' + id[0]).encode())