import sys

arquivo = sys.argv[1]

handler = open(arquivo, 'w')
for i in range(6001,7001):
    dados = "1" + " " + str(i) +" " + str(i+1) + "\n"
    handler.write(dados)
handler.write("2 7000\n")
handler.write("sair")
handler.close()
