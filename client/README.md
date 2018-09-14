# Trabalhos da disciplina de Sistemas Distribuídos

# Cliente

O cliente utiliza o maven como gerenciador de dependências e construção.
Outras tecnologias são utilzadas para construir a aplicação:

- [log4j2](https://logging.apache.org/log4j/2.x/): Biblioteca que provê implementação de geração de logs.
- [commons-io](https://commons.apache.org/proper/commons-io/): Biblioteca de APIs com operações de manipulação de arquivos mantida pela Apache Foundation.
- [commons-configuration2](http://commons.apache.org/proper/commons-configuration/): Biblioteca que provê configuração genérica de propriedades.
- [junit](https://junit.org/junit5): Biblioteca utilizada para construção de testes unitários automatizados. 
- [mockito](https://site.mockito.org/): Framework de mock para auxiliar na criação de testes unitários.
- [awaitility](https://github.com/awaitility/awaitility): Biblioteca java para testes de aplicações assíncronas.


Para construir a aplicação deve-se executar o seguinte comando:

```
$ mvn clean package -DskipTests
```

Para testar o cliente  em funcionamento podemos usar o ```netcat``` execute os seguintes comandos:

```
$ nc -l -p 4445
```

Então execute seguinte comando (após a construção):

```
$ java -jar target/projeto-sd-client.jar
CREATE 1
```

Você deverá acessar o netcat e digitar uma resposta, pois o cliente a ficará aguardando.


## Parametrização da aplicação

A aplicação pode ser parametrizada via arquivo de configuração, 
o arquivo de parametrização é o arquivo ```application.properties``` localizado em ```src/main/resources```.

Atualmente os seguintes parâmetros estão disponíveis:


| Parâmetro | Descrição | Exemplo |
| ----------| ----------|---------|
| ```server.port``` | Host de abertura de conexão com o servidor. Padrão: 127.0.0.1 | 127.0.0.1 |
| ```server.port``` | Porta de abertura de conexão com o servidor. Padrão: 4445 | 4445|

## Operações disponíveis

Atualmente as seguintes operações estão disponíveis:

| Comando | Descrição | Exemplo |
|---------|-----------|---------|
| ```READ/1```      | Lê um registro já cadastrado                                  | ```READ 1``` | 
| ```CREATE/1```    | Cria um registro com o valor passado                          | ```CREATE VALOR-01``` | 
| ```UPDATE/2```    | Atualiza um registro com o valor passado dada a chave passada | ```UPDATE 1 VALOR-01-ATUALIZADO``` | 
| ```DELETE/1```    | Deleta um registro já cadastrado                              | ```DELETE 1``` | 
| ```sair/0```      | Encerra o cliente                                             | ```sair``` | 

