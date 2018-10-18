configPath = '../config.txt'

def findInFile(path, string):
    f = open(path, 'r')
    l = f.readlines()

    i = 0

    while ('EOF' not in l[i]):
        if string in l[i]:
            return i
        i += 1
    f.close()


def getIpOffset():
    return findInFile(configPath, 'IP_OFFSET' )

def getPortOffset():
    return findInFile(configPath, 'PORT_OFFSET' )

def getNServers():
    return findInFile(configPath, 'N_SERVERS')

def getMKeys():
    return findInFile(configPath, 'M_Keys')

if __name__ == "__main__":
    # retornar posicao
    print(findInFile(configPath, 'IP_OFFSET'))


