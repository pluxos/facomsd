# Trabalho da disciplina de Sistemas Distribuídos

### Entrega 01

**Alunos:** 
* Bruno Monteiro
* Guilherme Eustáquio
* Mateus Benedini
* Matheus Santiago
* Rodrigo Souza

## Executar trabalho

### Antes de tudo:
#### Build do projeto:
`mvn clean install`

#### Servidor:
`mvn exec:java -Dexec.mainClass="server.ServerApplication"`

#### Cliente:
`mvn exec:java -Dexec.mainClass="client.ClientApplication"`

#### Testes:

##### Teste de integridade da recuperação do log:

###### Executar servidor com mock no log: 
`mvn exec:java -Dexec.mainClass="server.ServerApplication" -Dexec.args="src/test/resources/test.log"`
###### Executar rotina de testes:
`mvn exec:java -Dexec.mainClass="integration.IntegrationTests"`

