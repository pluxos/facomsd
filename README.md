# Trabalhos da disciplina de Sistemas Distribuídos


***Execução***

`python3 --version`
`Python 3.6.6`


- Para execução dos testes basta executar tests.py `python3 tests.py`
- Para executar cada modulo separado basta entrar na respectiva pasta e executar o `__init__.py` com `python3 __init__.py`


***Testes***

- Os no teste de ordem de execução como não há forma de esperar a thread de listen do client receber todas as respostas do servidor foi adicionado um input() para bloquear, basta digitar enter para continuar
- na pasta `logs/` estão todos os logs gerados pelos clients e servidor, os clients estão separados como:
    - clientUnico -> teste com um unico cliente
    - client-X    -> teste com multiplos clients onde X é o id do client


***Participantes***

- `Ronistone Gonçalves dos Reis Junior 11521BCC018`  
- `Gustavo Rosse de Oliveira 11021BSI227`  
- `Miguel Henrique de Brito Pereira 11511BCC035`