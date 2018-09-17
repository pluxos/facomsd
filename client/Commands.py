import threading

menu = """

commands available:
    read ID
    create ID VALUE
    update ID NEW_VALUE
    delete ID
    exit
    
"""


class Commands(threading.Thread):

    def __init__(self, communication):
        threading.Thread.__init__(self)
        self.stopRequest = threading.Event()
        self.communication = communication

    def run(self):
        print("Starting Thread of Commands...")

        while True:
            try:
                a = input(menu).split()
                if a[0] == "exit":
                    break
                elif a[0] == "read":
                    self.communication.new_message(a)
                    msg = self.communication.responses.get()
                    print(msg)
            except Exception as error:
                print(error)
                continue
