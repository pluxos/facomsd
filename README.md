# Trabalhos da disciplina de Sistemas Distribuídos
Grupo composto por:
- Daniel Marques
- Laura Banhareli

O servidor é iniciado executando o arquivo ServerThread.java. o mesmo aceita conexoes, cria uma thread para atender a requisição e continua aguardando por novasconexões.

O cliente é iniciado executando o arquivo SocketClient.java. o mesmo lista e valida localmente os comandos (create, readAll, update, delete) e, caso o comando seja válido, o envia para o servidor.

o database está no arquivo Database.java, e as informações dos comandos executados pelos clientes são salvos em um arquivo de texto, data.txt. Ao reiniciar a aplicaçao, os comandos são lidos no arquivo de texto e executados, evitando a perda de consistência.

