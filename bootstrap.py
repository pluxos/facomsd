'''
--------------------------------------------------------------------------
* Antes de inicializar os servidores, esse programa cria um pequeno      *
* script de inicializacao dos servidores.                                *
--------------------------------------------------------------------------
'''


def chunk_it(seq, num):
    avg = len(seq) / float(num)
    out = []
    last = 0.0

    while last < len(seq):
        out.append(seq[int(last):int(last + avg)])
        last += avg

    return out


# Abrindo o arquivo de configuracoes para gerar o inicializador
arquivo = open('.\\configs.ini', 'r')
configs = dict(eval(arquivo.read()))
arquivo.close()

servidores = configs.get('servidores')
bits = configs.get('bits')
chaves = 2**bits
universo = list(range(chaves))
intervalos = chunk_it(universo, servidores)

# Cria uma tabela que associa um ID a uma faixa de valores de chaves
faixas = {}

for i in range(servidores):
    faixas.setdefault(i, (min(intervalos[i]), max(intervalos[i])))

'''
launcher = open('launcher.cmd', 'w')
launcher.write('@echo off\n')

for i in faixas.keys():
    launcher.write('python servidor.py %d %d %d\n' % (i, faixas.get(i)[0], faixas.get(i)[1]))

launcher.write('@echo on\n')
launcher.close()
'''
launcher = open('launcher', 'w')

for i in faixas.keys():
    launcher.write('%d %d %d\n' % (i, faixas.get(i)[0], faixas.get(i)[1]))

launcher.close()