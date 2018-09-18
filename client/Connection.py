import socket

class Connection:

    def __init__(self, responses):
        print("Connect")
        self.socket = socket.socket()
        self.socket.connect(('localhost', 12345))

        self.responses = responses

    def sendRequest(self, msg):
        self.socket.send(msg)
        self.getResponse()

    def getResponse(self):
        buffer = ""
        BUFFERSIZE = 1024
        while True:
            msg = self.socket.recv(BUFFERSIZE)
            if len(msg) == BUFFERSIZE:
                buffer += msg.decode()
            else:
                buffer += msg.decode()
                break

        self.responses.put(buffer)
