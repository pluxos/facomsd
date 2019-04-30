# Trabalho de Sistemas distribuidos

- Este projeto está sendo desenvolvido para matéria Sistemas Distribuidos;
Faculdade de Computação - Universidade Federal de Uberlândia;
- Professor Lázaro Camargo (http://www.facom.ufu.br/~lasaro/)

Integrantes:  Deise Raiane Ribeiro dos Santos - 11911BSI276,
              Wallyson Pedrosa Ferreira - 11711BSI267,
              Rafael Denipote Ricci - 11511BSI255.

# Instruções do projeto
As classes .java esta em pacotes que ficam no diretório sd-trabalho/src/main/java.

As classes do pacote util são classes para auxiliar e padronizar a troca de mensagens entre cliente e servidor.


# Para executar o projeto é necessário 


Executar primeiro a classe Servidor.

Executar depois classe MenuCliente, essa classe pode ser executada quantas vezes quiser, já o servidor vai criar uma thread para atender cada cliente.

# Instruções do cliente


As operações para o Cliente(MenuCliente) são: READ, CREATE, UPDATE, DELETE, SAIR e AJUDA.

A operação READ é para consultar um valor inserido no banco, é necessário informar a chave para realização da consulta.

A operação CREAT é para inserir um valor no banco. É necessário informar a chave e o valor a ser inserido.

A operação UPDATE é para atualizar uma.

Operação SAIR finaliza a conexão do cliente com o servidor.

Operação AJUDA mostra todas as operações possíveis.

Caso informe uma operação incorreta, é enviado uma mensagem de erro para o cliente e para informar uma operação válida (o cliente não é desconectado do servidor).
