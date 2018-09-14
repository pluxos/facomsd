from Commands import Commands
from Communication import Communication


communication = Communication()
command = Commands(communication)

command.start()
communication.start()

command.join()

communication.join(5.0)




