

class MyDict:

    def __init__(self):
        self.data = {}

    def get(self, id):
        return self.data.get(id)

    def put(self, id, value):
        self.data[id] = value

    def pop(self, id, default=None):
        return self.data.pop(id, default)