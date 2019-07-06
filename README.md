#Alunos:

```
João Paulo de Oliveira
Lucas Rossi Rabelo
Antônio Carlos Neto
Gustavo de Faria Silva
```

##Build e Testes

Para executar os servers, entre na pasta Server e execute:
```
mvn test
```

Para executar o client, entre na pasta client e execute:
```
mvn clean package exec:java -Dexec.mainClass="client.Client" -Dexec.args="127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
```