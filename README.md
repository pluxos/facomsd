# Trabalhos da disciplina de Sistemas Distribuídos

Para submeter, crie um fork deste repositorio. Trabalhe no seu fork e um branch com o nome SUBMISSAO_X, onde X é o número da entrega a ser feita. 

Quando estiver pronto para submeter a versao final, faca um pull request para este fork aqui.

Para cada submissão, altere o arquivo README.md ***NO SEU BRANCH*** para conter o nome dos componentes do grupo e instruções de como executar o projeto e testes

# Como executar?
Instruções para executar cada projeto estão disponíveis em:</br>

[DS-Server (Entrega 1)](https://github.com/MarcusAdriano/DS-Server/tree/Submit/ONE)</br>
[DS-Client (Entrega 1)](https://github.com/MarcusAdriano/DS-Server/tree/Submit/ONE)

#### Repositórios originais:

https://github.com/MarcusAdriano/DS-Server </br>
https://github.com/MarcusAdriano/DS-Client

> Criamos uma branch separada no repositório original para não replicarmos informações de execução dos projetos, todas as informações na branch linkada acima possuem alterações até o dia 30/04/19 às 23:59.

# Realizar vários testes?

No Ubuntu é possível instalar o expect: 

```bash
$ sudo apt install expect
```

Logo após instalar o mesmo, basta executar o arquivo RunExpect com o expect:

```bash
$ expect RunExpect.sh
```

Com isso é possível executar uma sequência de comandos através de um cliente. Abrindo vários terminais é possível executar vários clientes automaticamente com o expect.

O servidor suporta vários clientes simultâneos e faz uso de Non-Blocking I/O.

# Grupo: 

Marcus Adriano Ferreira Pereira - 11521BCC002 </br>
Lucas Tannús Vieira - 11611BCC049 </br>
Isadora Rezende Lopes - 11611BCC051
