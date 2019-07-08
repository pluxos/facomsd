# Entrega 3 -

### Grupo 8 - BCC:

Guilherme Fagotti
João Marcos Gomes
Marilia Leal
Rafael Morais de Assis


## Run
### Servers
mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="1 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.server.Servidor" -Dexec.args="2 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

### Clients
mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.client.Cliente" -Dexec.args="127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

### Cliente de teste

mvn exec:java -Dexec.mainClass="atomix_lab.state_machine.client.ClienteTeste" -Dexec.args="127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
