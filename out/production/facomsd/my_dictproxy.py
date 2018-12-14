from concoord.clientproxy import ClientProxy

class MyDict:

    def __init__(self, bootstrap):
        self.proxy = ClientProxy(bootstrap, token='None')

    def __concoordinit__(self):
        return self.proxy.invoke_command('__concoordinit__')

    def get(self, id):
        return self.proxy.invoke_command('get', id)

    def put(self, id, value):
        return self.proxy.invoke_command('put', id, value)

    def pop(self, id, default=None):
        return self.proxy.invoke_command('pop', id, default)