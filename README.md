# Trabalho de Sistemas Distribuidos : (UFU 2019.1)

Grupo:

- Guilherme Fagotti
- João Marcos Gomes
- Marilia Leal
- Rafael Morais de Assis

## Como executar

**Executar no Linux (bash)**

No linux (e no windows se você descobrir como) você pode executar script `.sh` que executa vários comandos encadeados.

Há os arquivos `serv.sh` e `cli.sh` que foram configurados para compilar e executar os arquivos de Servidor e Cliente.

Então execute (em ordem e em terminais diferentes):

- `bash serv.sh` : Para executar o SERVIDOR
- `bash cli.sh` : Para executar o CLIENTE

**No Windows**

Caso os srcipt `.sh` não funcionarem, você pode executar tudo manualmente.

Descrição dos comandos JAVA:

- `javac File_java.java` : Compila o arquivo `File_java.java`
- `java File_java `: Executa o arquivo `File_java.class` (o ByteCode)

Assim para executar o projeto será:

```
javac Servidor.java
java Servidor

javac Cliente.java
java Cliente
```

Para remover arquivos .class (os arquivos compilados (opcional)):

`find . -type f -name '*.class' -delete`
