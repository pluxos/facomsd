# Trabalhos da disciplina de Sistemas Distribuídos

Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome SUBMISSAO_X, onde X é o número da entrega a ser feita. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes.


## Membros

- Brian Batista
- Igor de Souza Silva
- Murilo Rezende Montano
- Pedro Henrique Rodrigues Marques Dos Santos
- Vitor Ferraz do Amaral Mello

## Pré-requisitos

Para a execução desse projeto serão necessários o Java Runtime Environment, o Java SE Development Kit e a ferramenta de automação de compilação Maven.

### Instalação

Primeiramente, atualize a lista de pacotes:
```
$ sudo apt update
```

Instale o Java Runtime Environment com o comando:
```
$ sudo apt install default-jre
```

Instale o Java SE Development Kit:
```
$ sudo apt install default-jdk
```

Por fim, instale a ferramenta Maven com:
```
$ sudo apt install maven
```

## Execução:

Para iniciar a execução do servidor, utilize o comando: 
```
$ mvn package exec:java -Dexec.mainClass=br.com.ufu.javaGrpcClientServer.server.JavaServer
```

Após iniciado o servidor, utilize o comando para a execução do cliente: 
```
$ mvn package exec:java -Dexec.mainClass=br.com.ufu.javaGrpcClientServer.client.JavaClient
```
