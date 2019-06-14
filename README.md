# Trabalho da disciplina de Sistemas Distribuídos

### Entrega 02

**Alunos:** 
* Bruno Monteiro
* Guilherme Eustáquio
* Mateus Benedini
* Matheus Santiago
* Rodrigo Souza

O projeto agora foi arquiteturado para um projeto multi módulo, separando cliente e servidor para melhor distinção do código.

Para facilitar a execução do trabalho, foi criado um shell script que cria 4 servidores, com portas, respectivamente, 12345, 12346, 12347 e 12348. Além disso, cada uma delas possui um diretório diferente (Server0, Server1, Server2 e Server3) para fazer a rotina de snapshot e log independentes para cada servidor.

Para auxílio do desenvolvimento, foram criados os comandos 'ft' (finger table), 'range' e 'key', recebidos pelo teclado, em cada servidor, que mostram as informações referentes ao mesmo.


## Executar trabalho

1) Executar o arquivo 'startServers.sh', para inicializar os 4 servidores (Linux).
** Em caso de outro SO, dentro do diretório Server: mvn exec:java -Dexec.args=logs/{pastaServidor}/ localhost {porta}

2) Para executar o cliente, dentro do diretório Client, mvn exec:java -Dexec.args=localhost {porta} (se rodar o script, por exemplo, porta=12345)


