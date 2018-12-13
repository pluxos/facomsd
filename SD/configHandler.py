configPath = './config.txt'


def findInFile(path, string):
    f = open(path, 'r')
    lines = f.readlines()
    i = 0
    while 'EOF' not in lines[i]:
        if string in lines[i]:
            return str(lines[i]).split()[1]
        i += 1
    f.close()


def getIpOffset():
    return findInFile(configPath, 'IP_OFFSET')


def getPortOffset():
    return findInFile(configPath, 'PORT_OFFSET')


def getNServers():
    return findInFile(configPath, 'N_SERVERS')


def getMKeys():
    return findInFile(configPath, 'M_Keys')
