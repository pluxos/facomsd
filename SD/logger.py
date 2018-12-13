import map_pb2
import service
import os


grpcByCommandCode = {
    '1': service.create,
    '2': service.retrieve,
    '3': service.update,
    '4': service.delete
}


# Escreve no log
def logMessage(message, path):
    stringMessage = '¬¬'
    stringMessage += str(message.id) + '¬¬'
    stringMessage += message.command + '¬¬'
    stringMessage += str(message.data.key) + '¬¬'
    stringMessage += message.data.value + '¬¬'

    command_log = open(path, "a")
    command_log.write(stringMessage + '\n')
    command_log.close()


# Salva sanapshot
def saveSnapshot(database, snap_path):
    f = open(snap_path, "a+")
    f.write(str(database))
    f.close()


# Recupera o BD com dados do log e do snapshot
def recover_database_from_snap_and_log(snap_folder_path, log_folder_path):
    logs = os.listdir(log_folder_path)
    snaps = os.listdir(snap_folder_path)
    i = len(snaps) - 1
    bd = {}
    try:
        for arquivo in reversed(snaps):
            try:
                snap = open('%s/%s' % (snap_folder_path, arquivo), 'r')
                bd = dict(eval(snap.read()))
                snap.close()
                break
            except IOError:
                i = i - 1
        for file in logs[i:]:
            recoverFromLog(bd, log_folder_path + '/' + file)

        recent_snaps = os.listdir(snap_folder_path)
        for s in recent_snaps:
            os.remove(snap_folder_path + '/' + s)

        return bd
    except:
        for s in recent_snaps:
            os.remove(snap_folder_path + '/' + s)
        return {}




def recoverFromSnapshot(path):
    try:
        f = open(path, 'r') or ''
        dbstring = f.read() or '{}'
        return dict(eval(dbstring))
    except:
        print('-Aviso: arquivo de snapshot não encontrado em ' + path)


def recoverFromLog(database, path):
    log_list = retrieveLog(path)
    for message in log_list:
        print(message.data)
        command = message.command
        key = message.data.key
        value = message.data.value
        grpcByCommandCode[command](database, key, value)
    print(database)


def retrieveLog(path):
    command_log = open(path, "r")
    lines = command_log.readlines() or ''
    messageList = []
    for line in lines:
        lineString = line.split('¬¬')
        messageList.append(
            map_pb2.Message(
                id=int(lineString[1]),
                command=lineString[2],
                data=map_pb2.Data(
                    key=int(lineString[3]),
                    value=lineString[4]
                )
            )
        )
    return messageList

