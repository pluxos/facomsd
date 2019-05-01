# Trabalhos da disciplina de Sistemas Distribuídos

Grupo:  Danilo Vieira |
        João Jacó |
        Pedro Henrique Fernandes |
        Vinicius Riquieri |

INSTRUÇÕES

Para executar o projeto, comece primeiro rodando o arquivo "Servidor.java" e depois o
arquivo "ClienteMenu.java"

em "ClienteMenu" é onde será mostrado o menu com as opções de operações:

C - Create |
R - Read |
U - Update |
D - Delete |
X - Sair |

"Servidor" é a classe que irá iniciar as Threads do Servidor que sao:

- "Servidor" - Recebe os comandos e os colocam na fila f1
- "Servidor2" - Copia os comandos da f1 para f2 e f3
- "Servidor3" - Registra os comandos de f2 em disco --incompleto
- "Servidor4" - Aplica os comandos ao banco(mapa)

"ClienteListener" é iniciada em "ClienteMenu" e será a Thread que receberá as respostas
das operações feitas (Servidor4)

"port1" e "port2" são os arquivos de conviguração das portas utilizadas na conexão

"log" seria o arquivo onde se registraria as operações

O QUE NÃO FOI CONCLUÍDO:

- A Thread de log, não conseguimos gravar as informações no arquivo de forma confiável,
  por consequência a parte de ler o arquivo de log não foi finalizada também

- cliente de testes
