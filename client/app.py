from Commands import Commands
from Listener import Communication
from Connection import createSocket


socket = createSocket()

communication = Communication(socket)
command = Commands(socket)

command.start()
communication.start()

command.join()

communication.join(5.0)




