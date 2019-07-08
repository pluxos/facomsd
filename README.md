# Trabalho de Sistemas Distribuidos : (UFU 2019.1) - Útima Entrega

Grupo 8 - BCC:

+ Guilherme Fagotti
+ João Marcos Gomes
+ Marilia Leal
+ Rafael Morais de Assis

## Build

mvn clean compile

## Run

### Servers

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="1 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="2 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

### Clients

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.client.Cliente" -Dexec.args="127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

### Cliente de Teste

Executar em 3 terminais os 3 'Servers' anteriores e em seguida executar o cliente de teste Abaixo:

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.client.Cliente" -Dexec.args="127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002 teste"
