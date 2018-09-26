import sys

arquivo = sys.argv[1]

handler = open(arquivo, 'w')
for i in range(5001,6001):
    dados = "1" + " " + str(i) +" " + str(i+1) + "\n"
    handler.write(dados)
handler.write("2 6000\n")
handler.write("sair")
handler.close()
