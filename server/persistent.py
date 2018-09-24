from asyncService import AsyncService

from queue import Empty


class Persistent(AsyncService):

    def __init__(self, toPersist):
        AsyncService.__init__(self)
        self.toPersist = toPersist
        self.data = {}


    def run(self):

        while not self.stopEvent.isSet():

            try:
                request = self.toPersist.get(True, 1)

                r = request.split()

                if r[0].upper() == "READ":
                    self.read(request)
                if r[0].upper() == "CREATE":
                    self.create(request)
                if r[0].upper() == "UPDATE":
                    self.update(request)
                if r[0].upper() == "DELETE":
                    self.delete(request)

            except Empty:
                continue
        for k,v in self.data:
            print(k + ": " + v)

    def read(self, request):
        r = request.split()
        try:
            print(self.data[r[1]])
        except KeyError:
            print("Key not found!")

    def create(self, request):
        r = request.split()
        self.data[r[1]] = r[2]
        print("Created -> " + r[1] + ": " + r[2])

    def update(self, request):
        r = request.split()
        self.data[r[1]] = r[2]
        print("Updated -> " + r[1] + ": " + r[2])

    def delete(self, request):
        r = request.split()
        self.data.pop(r[1], None)
        print("Deleted -> " + r[1])

