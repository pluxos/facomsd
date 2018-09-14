# Trabalhos da disciplina de Sistemas Distribuídos

# Servidor

O server utiliza o maven como gerenciador de dependências e construção.
Para construir a aplicação deve-se executar o seguinte comando:

```
$ mvn clean package -DskipTests
```

Para executar os testes unitários do servidor deve-se executar o seguinte comando

```
$ mvn clean test 
```

Para iniciar a aplicação deve-se executar o seguinte comando (após a construção):

```
$ java -jar target/projeto-sd-1.0-SNAPSHOT.jar
```

Para testar o servidor  em funcionamento podemos usar o ```telnet``` execute os seguintes comandos:

```
$ telnet localhost 4445
READ TESTE-01
```

A resposta para o comando anterior será:

```
Command REQUESTED: CREATE TESTE-01
Command RESPONSE: TESTE-01
``` 

## Parametrização da aplicação

A aplicação pode ser parametrizada via arquivo de configuração, 
o arquivo de parametrização é o arquivo ```application.properties``` localizado em ```src/main/resources```.

Atualmente os seguintes parâmetros estão disponíveis:


| Parâmetro | Descrição | Exemplo |
| ----------| ----------|---------|
| ```server.port``` | Porta de execução do servidor. Padrão: 4445 | 4445|

## Operações disponíveis

Atualmente as seguintes operações estão disponíveis:

| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| ```READ/1```      | Lê um registro já cadastrado                                  | ```READ 1``` | 
| ```CREATE/1```    | Cria um registro com o valor passado                          | ```CREATE VALOR-01``` | 
| ```UPDATE/2```    | Atualiza um registro com o valor passado dada a chave passada | ```UPDATE 1 VALOR-01-ATUALIZADO``` | 
| ```DELETE/1```    | Deleta um registro já cadastrado                              | ```DELETE 1``` | 

