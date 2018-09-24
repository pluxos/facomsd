from commands import Commands
from listener import Communication
from connection import createSocket


socket = createSocket()

listener = Communication(socket)
command = Commands(socket)

command.start()

listener.start()

command.join()

listener.join(5.0)




