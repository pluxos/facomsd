from Listener import Listener
from queue import Queue


requests = Queue()

listener = Listener(requests)
listener.listen()
