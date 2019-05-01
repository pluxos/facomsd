"# Sistemas-Distribuidos" 
----------------------------
Alexandre Pereira Marcos
Natan Luis Silva Rodvalho
Gustavo Miranda
Lara Caroline

---------------------------------------------------------------------------------------------------------
Projeto foi construído pela IDLE NetBeans 8.2,  todas as classes estão na pasta src/sistemas_districuidos
basta abrir o projeto pela IDLE executar os passos a seguir.

Para rodar o banco primeiro é necessário executar a classe servidor, na qual as portas e o 
host que o cliente precisarão para fazer conexão estão definidas no arquivo Porta_e_host.txt.

Depois para a interação com a classe servidor deve ser executado a classe cliente, que já está
a porta e o host, portanto é só executar a classe. Porém, como foi usado threads, delimitamos o
número de clientes a serem atendidos que são de no máximo 15, se mais clientes forem conectados
eles aguardam em uma fila thread até ser liberado.