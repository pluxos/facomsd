# Trabalho da disciplina de Sistemas Distribuídos

### Entrega 02

**Alunos:** 
* Bruno Monteiro
* Guilherme Eustáquio
* Mateus Benedini
* Matheus Santiago
* Rodrigo Souza

O projeto agora foi arquiteturado para um projeto multi módulo, separando cliente e servidor para melhor distinção do código.

Para facilitar a execução de um cliente de teste (ou stress), foi criado alguns arquivos txt's padrões, os mesmos estão 
disponíveis na pasta `Client/src/test/resources/`. Pedimos para que eles não sejam alterados, pois utilizamos os mesmos
na execução dos testes que garantem a integridade da base de dados.

## Executar trabalho

### Antes de tudo:

Os Comandos listados a baixo deveram ser realizados na raiz do projeto, ou seja, na pasta `facomsd/`.

#### Build do projeto:
`mvn clean install`

#### Servidor:
`mvn exec:java -pl Server -Dexec.args="<arg0> <arg1> <arg2> <arg3> <arg4>"`

##### *Argumentos*
* arg0 -> Pasta onde o servidor irá salvar seus logs e snapshots! Lembre-se que você deve passar um path considerando a 
pasta onde você vai iniciar o servidor.
* arg1 e arg2 -> Ip e porta do servidor!
* arg3 e arg4 -> Ip e porta de outro servidor, para o primeiro contato com o anel lógico! (Não passar esses argumento 
caso seja o primeiro servidor)

##### *Exemplos*

*Primeiro Servidor*

`mvn exec:java -pl Server -Dexec.args="Server/logs/Server0/ localhost 12345"`

*Segundo Servidor*

`mvn exec:java -pl Server -Dexec.args="Server/logs/Server1/ localhost 12346 localhost 12345"`

##### *Melhorias*

Disponibilizamos uma thread que lê do teclado alguns comandos no servidor.

*Comandos*

* `key`   -> Apresenta a chave que o servidor pegou ao entrar no Anél lógico;
* `range` -> Apresenta qual é o range de chaves que o servidor possui naquele momento;
* `ft`    -> Apresenta a finger table do servidor, sempre mostrando a chave calculada na função `successor()` para cada 
posição da tabela;

#### Cliente:
`mvn exec:java -pl Client -Dexec.args="<arg0> <arg1> <arg2>"`

##### *Argumentos*
* arg0 e arg1 -> Ip e porta do servidor que será enviado os comandos.
* arg2 -> Caso você queira que o cliente execute comandos que esteja em algum arquivo, passe o path do arquivo neste argumento

##### *Exemplos*

*Cliente via Teclado*

`mvn exec:java -pl Client -Dexec.args="localhost 12345"`

*Cliente via Arquivo*

`mvn exec:java -pl Client -Dexec.args="localhost 12346 Client/src/test/resources/stress1.txt"`

***Lembrando que, todos os paths enviados por parâmetro devem ser considerados a partir da pasta da qual você está 
executando os comandos acima, ou o caminho absoluto desde o diretório raiz `/`***


#### *Shells*

Na pasta `shells/` estão disponíveis três arquivos shell script, dispostos em duas pastas. 

* Caso você utilize uma distro com interface gráfica *gnome*, ou intefaces baseadas no gnome, utilize os arquivos da pasta 
 `gnome`, pois os mesmos executam os comandos via `gnome-terminal`.
* Caso você utilize uma distro com interface grafica *xfce*, ou interfaces basedas no xfce, utilize os arquivos da pasta
 `xfce4`, pois os mesmos executam os comandos via `xfce4-terminal`.

##### Arquivos

`./startServers.sh`

Inicializa 1 servidor a cada 20 segundos, até chegar a 4 servidores;

`./server-client.sh`

Inicializa 1 servidor e um cliente para enviar comandos do teclado para este servidor, a cada 20 segundos incia-se outro
servidor, até chegar a 4 servidores ativos com seus respectivos clientes.

`./testes.sh <arg0> <arg1>`

* `arg0` -> Quantidade de servidores que deve ser inicializada;
* `arg1` -> Quantidade de Clientes que deve ser inicializada; (Estes clientes executam os comandos dispostos nos 
arquivos de stress);

Inicializa `arg0` servidores e `arg1` Clientes. Lembrando que o numero de clientes deve ser menor ou igual ao de servidores,
pois cada cliente só terá um servidor para executar os comandos

* Estes arquivos devem ser executados a partir da pasta `facomsd/`, sendo assim, ao executar o arquivo `startServers.sh`
o comando deverá ser `./shells/<interface>/startServers.sh`;
