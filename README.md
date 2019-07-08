# Trabalhos da disciplina de Sistemas Distribuídos

- Etapa 3 do trabalho.
- Faculdade de Computação - Universidade Federal de Uberlândia.
- Professor Lázaro Camargo (http://www.facom.ufu.br/~lasaro/).
- Integrantes:  Deise Raiane Ribeiro dos Santos - 11911BSI276, 
              Wallyson Pedrosa Ferreira - 11711BSI267, 
              Rafael Denipote Ricci - 11511BSI255.

## Descrição

O projeto é uma aplicação cliente-servidor, em que a comunicação é feita por socket.
Multithread para atender vários clientes ao mesmo tempo
Será utilizando o atomix para replicar os servidores
O cliente pode se conectar por qualquer um dos servidores
Devido a natureza do atomix de ser consistente, toda a parte de logs de arquivos não foram necessárias nesta etapa

## Instruções
# Server

- Execute os 3 servidores

mvn exec:java -Dexec.mainClass="br.ufu.sd.main.StartServer" -Dexec.args="1"

mvn exec:java -Dexec.mainClass="br.ufu.sd.main.StartServer" -Dexec.args="2"

mvn exec:java -Dexec.mainClass="br.ufu.sd.main.StartServer" -Dexec.args="3"


- O único argumento necessário é o id do server que deseja executar
- As demais ficam em um arquivo de configuração 
- O projeto só funciona com os 3 onlines
- As configurações dos servidores ficam no arquivo cluster-config.txt
  - No arquivo de configuração o primeiro paramentro é o id do servidor para o atomix
  - O segundo e o ip do membro do servidor para o atomix
  - O terceiro é a porta do server para o atomix
  - O quarto é a porta para o ServerSocket do servidor
 - É possível passar dois comandos diretamente para os servidores em seus respectivos terminais
   - close - para finalizar aquele servidor
   - clear - para limpar o banco ( Util para rodar antes dos testes, pois pode haver já alguns dados naquele server que pode comprometer os resultados), muito cuidado, pois esse comando limpa todos os bancos de todos os servidores devido a natureza do atomix. 
  
# Cliente interativo

- Com os servers onlines execute


mvn exec:java -Dexec.mainClass="br.ufu.sd.main.Client" -Dexec.args="localhost 5051"


- Ip e porta em que se deseja conectar o cliente como argumentos
- Veirifar o arquivo "cluster-config.txt" para saber as portas do socket dos servidores

# Testes

- Alguns testes criados para o servidor
- Para mais informações leia o arquivo "descrioes-test.txt"
- É necessários os 3 servers onlines para executar os testes

### Teste Ok e NOK
Execute o script "test-oknok.sh"

### Teste OPERACOES
Execute o script "test-operacoes.sh"

### Teste READ
Execute o script "test-read.sh"

### Teste de Consistência
Execute o script "test-operacoes.sh", reinicie os 3 servidores manualmente, depois execute o script "test-read.sh"

### Teste Ordem de Excecucão
Execute os script "test-ordem-exec.sh"

### Teste Multiplos Clientes
Execute o script "test-multclient-exec.sh"
