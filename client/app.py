from Commands import Commands
from Communication import Communication
from queue import Queue


requests = Queue()
responses = Queue()

communication = Communication(requests, responses)
command = Commands(communication)

command.start()
communication.start()

command.join()

communication.join(5.0)




