# Trabalhos da disciplina de Sistemas Distribuídos


***Execução***

`Docker version 18.05.0-ce, build f150324`

- Entre no pasta raiz do projeto
- Antes da execução precisamos realizar o build da imagem docker do projeto com `./build_container.sh`
- Para inicializar os servidores execute `./startContainer.sh`
    - Será perguntado os seguintes pontos:
        - Quantos nós deve ser inicializados
        - Quantos bits a key do banco terá
        - Se deseja destruir os containers se já existirem **(Muita atenção aqui pois por padrão é escolhido por remover, para não remover digite 'n' sem as aspas)**


***LOGS***

- Para visualizar o log de um servidor bastar utilizar o comando `docker logs <nome do servidor>`, por padrão o script `startContainer.sh` nomeia cada servidor como `node_N_i` onde N é a quantidade de servidores no anel e i é o id do servidor

***Client***
- O client agora recebe um ip para conectar aos servidores, ou seja, para iniciar um client basta entrar na pasta `./cliente` e executar python3 `__init__.py <IP de um dos servidores>`

***Server***
- A inicialização de um servidor é feito na pasta `./server` utilizando `python3 __init__.py [IP] M N id` se o ip não é passado aquele servidor irá inicializar um novo anel. Este é o processo realizado pelo script `startContainer.sh`

***Pontos Importantes***

- Para restartar o servidor basta executar o script `startContainer.sh` **tomando cuidado de utilizar a mesma quantidade de servidores e escolhendo 'n' para a não destruição dos containers**
- Os arquivos de snapshot e logs criados pelos servidores são guardados dentro de seus devidos containers, por este motivo temos que ter cuidado ao destruir um container.


***Participantes***

- `Ronistone Gonçalves dos Reis Junior 11521BCC018`  
- `Gustavo Rosse de Oliveira 11021BSI227`  
- `Miguel Henrique de Brito Pereira 11511BCC035`