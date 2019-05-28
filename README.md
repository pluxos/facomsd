# Trabalhos da disciplina de Sistemas Distribuídos

## Instruções para o grupo:

### GIT

#### Enviar para a master
Cada um cria uma branch com o nome que quiser (ex. branch_atual) e desenvolve nela. Quando alguém precisar de uma alteração, fazer push para a master com o comando:

 ``` git push origin branch_atual:master```

Não tem como fazer pelo vscode. Isso é uma alternativa mais rápida do que o **pull request**. Quem quiser, me pede ajuda pra configurar o git para não pedir login e senha no push.

#### Trazer da master

1) Atualize as alterações da master
2) Mude para a sua branch
3) Traga as alterações da master
```bash
git pull            #( ou aquela rodinha do vscode)
git checkout branch_atual #Sua branch de desenvolvimento
git merge master    #Não tem equivalente na interface gráfica (não q eu saiba)
```
### Maven

#### Compilando o projeto
1) Entre na pasta Client e execute: `mvn clean package`
2) Entre na pasta Server e execute: `mvn clean package`

Com esses comandos, ele vai criar o arquivo `.jar`, como configurado no pom.xml do Client e Server.

#### Executando o projeto
1) Entre na pasta output
2) Execute o Server: `java -jar Server-1.0-SNAPSHOT.jar`
3) Execute o Client: `java -jar Client-1.0-SNAPSHOT.jar`

O Server irá criar os arquivos de log e snap na pasta atual (`output`) e recuperar se houver antigos.

## Instruções do projeto:
Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome SUBMISSAO_X, onde X é o número da entrega a ser feita. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes.


# Alunos:

```
João Paulo de Oliveira
Lucas Rossi Rabelo
Antônio Carlos Neto
Gustavo de Faria Silva
```

## Build e Testes

Na pasta output, basta executar o arquivo ```script.sh```
