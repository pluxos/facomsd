# Trabalhos da disciplina de Sistemas Distribuídos

- Este projeto está sendo desenvolvido para matéria Sistemas Distribuidos.
- Faculdade de Computação - Universidade Federal de Uberlândia.
- Professor Lázaro Camargo (http://www.facom.ufu.br/~lasaro/).
- Integrantes:  Deise Raiane Ribeiro dos Santos - 11911BSI276, 
              Wallyson Pedrosa Ferreira - 11711BSI267, 
              Rafael Denipote Ricci - 11511BSI255.
# Descrição 
- Segunda etapa do projeto em que agora a comunicação entre cliente e servidor é feita via gRPC e implementação de uma rede de servidores (chord) e uma mudança no sistema de arquivos, 3 logs vão ser criados e apos os 3 encherem, no caso do teste vão ser 3 comandos por log, vai ser criado um snapshot do server e guardado em arquivo. Obs. Não conseguimos implementar o chord, existem algumas classe para criar o chord, coordenar os servidores e encaminhar as requisições que não são utilizadas.
# Instruções do projeto
- As classes .java estão em pacotes que ficam no diretório sd-trabalho/src/main/java.
- Executar primeiro a classe IniciaServer no pacote com.sd.etapa2.test.
- Executar depois classe MenuCliente, essa classe pode ser executada quantas vezes quiser, já o servidor vai criar uma thread para atender cada cliente.
# Instruções do cliente

- As operações para o cliente (MenuCliente) são: READ, CREATE, UPDATE, DELETE, SAIR e AJUDA.
- A operação READ é para consultar um valor inserido no banco, é necessário informar a chave para realização da consulta.
- A operação CREAT é para inserir um valor no banco. É necessário informar a chave e o valor a ser inserido.
- A operação UPDATE é para atualizar uma.
- Operação SAIR finaliza a conexão do cliente com o servidor.
- Operação AJUDA mostra todas as operações possíveis.
- Caso informe uma operação incorreta, é enviado uma mensagem de erro para o cliente e para informar uma operação válida (o cliente não é desconectado do servidor).
# Testes
- Instruções para os testes no arquivo "descricao-tests.txt"
