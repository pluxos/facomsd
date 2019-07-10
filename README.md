# Trabalhos da disciplina de Sistemas Distribuídos

Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome SUBMISSAO_X, onde X é o número da entrega a ser feita. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes.


Integrantes Grupo:
 - Rafael Faria Macedo          11621BSI243
 - Isadora Rocha de Paula       11421BSI246
 - Gabriel Vieira Dias		    11321BSI240
 - Rafael Francisco Cunha Reis  11521BSI255


EXECUÇÃO:

Ter instalado na máquina o Apache Maven e JAVA 1.8

Compilar o projeto:
 - Na pasta raiz executar o comando via linha de comando:
    mvn clean install


Executar o Server:
- na pasta /target/classes executar o comando:
   mvn exec:java -Dexec.mainClass="com.sd.projeto1.main.Server" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

Executar o Client:
- na pasta /target/classes executar o comando:
   mvn exec:java -Dexec.mainClass="com.sd.projeto1.main.Client" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"


Aparecerá o menu:

===============================
Digite a operação:
1 - Inserir
2 - Atualizar
3 - Excluir
4 - Buscar
5 - Sair
Opção:

A partir desse ponto é só escolher a opção e digitar o que se pede
