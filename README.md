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

#### Compilando e Executando o projeto
1) Entre na pasta Server e execute: `mvn clean package`
2) Execute o Server: `mvn exec:java -Dexec.mainClass=server.ServerGrpc`
4) Execute quantos Servers forem necessários, as chaves e portas devem ser fornecidas por meio de scanf para dar liberdade de fazer testes. IMPORTANTE: O primeiro Server deve ter a porta 2000.
4) Execute o Client: `mvn exec:java -Dexec.mainClass=server.ClientGrpc`
5) Execute quantos Clients forem necessários, não é necessário fazer nenhum ajuste adicional. O Client deverá se conectar com o servidor com a porta 2000.

Foi deixado Prints para acompanhar a lógica do código.
O Server irá criar os arquivos de log e snap na pasta atual (`Server`) e recuperar se houver antigos("DEVERIA PELO MENOS"). ***NÃO FOI IMPLEMENTADA DELEÇÃO DE SERVIDORES.***

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
