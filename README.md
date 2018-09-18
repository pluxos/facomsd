# Trabalhos da disciplina de Sistemas Distribuídos

Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome 2018_2_X, onde X é um identificador atribuído pelo professor para o seu grupo de trabalho. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes.


Este projeto é multi-modulo do maven e contém os seguintes projetos:

- core: Biblioteca padrão contendo dependências comuns.
- server: Servidor proposto para a aplicação.
- client: Cliente proposto para a aplicação.

Na pasta raiz é possível executar comandos maven que fará a execução do comando em todos submódulos. 

Para construir todos os módulos execute:

```
$ mvn clean package
```
