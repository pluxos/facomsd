# Trabalhos da disciplina de Sistemas Distribuídos


***Execução***

`Docker version 18.05.0-ce, build f150324`

- Entre no pasta raiz do projeto
- Antes da execução precisamos realizar o build da imagem docker do projeto com `./build_container.sh`
- Para inicializar os servidores com replicas execute `startContainerWithReplica.sh`
    - Será perguntado os seguintes pontos:
        - Quantos nós deve ser inicializados
        - Quantos bits a key do banco terá
        - Quantas replicas serão criadas para cada nó
        - Se deseja destruir os containers se já existirem **(Muita atenção aqui pois por padrão é escolhido por remover, para não remover digite 'n' sem as aspas)**

***Virtual Environment***

- Para utilizar o virtual environment basta instalar na sua máquina, `use pip3 install virtualenv`
- Para criar o virtual environment do client use `virtualenv -p python3 env-client` 
- Para criar o venv do server `virtualenv -p python2 env-client`
- depois entre no venv com . `<path to venv>/bin/activate` e instale as dependencias com `pip install -r requirements.txt`

    - `OBS.: se estiver utilizando o script de start não há necessidade de utilizar o venv para o mesmo pois o próprio container já tem o ambiente montado.`
    - `Apenas o venv do client é necessário`

***LOGS***

- Os logs agora são guardados na pasta em que vocẽ executar o script `startContainerWithReplica.sh` eles teram o nome `output_node_N_i_j` onde `N` é a quantidade de servidores no anel e `i` é o id do servidor e `j` é o id do servidor no cluster do nó

***Client***
- O client agora recebe um ip para conectar aos servidores, ou seja, para iniciar um client basta entrar no `virtual environment` e entrar na pasta `./cliente` e executar `python3 __init__.py <IP de um dos servidores>`

***Server***
- A inicialização de um servidor deve ser realizado pelo script de start. Caso seja necessário startar o servidor de forma manual dê uma olhada em como o processo é feito no `./server/startServer.sh`.

***Pontos Importantes***

- Para restartar o servidor basta executar o script `startContainerWithReplica.sh` **tomando cuidado de utilizar a mesma quantidade de servidores e escolhendo 'n' para a não destruição dos containers**


***Participantes***

- `Ronistone Gonçalves dos Reis Junior 11521BCC018`  
- `Gustavo Rosse de Oliveira 11021BSI227`  
- `Miguel Henrique de Brito Pereira 11511BCC035`