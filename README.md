# Server-Client

### Sistemas Distribuidos - 2019/1 - Parte 2

## Classes e Funcionamento do Projeto

#### Server



1. Compilar e executar o programa : 

    * ./gradlew clean build *
    * Execute primeiramente o AppServer
    * ./src/main/java/serverclient/server/AppServer.java
    * Após, execute quantos clients você quiser.
    * ./src/main/java/serverclient/client/AppClient.java

2. Como está o projeto na parte do Client:
    * O Client possui 3 threads : A thread consumidora (AnswerPresentationThread), e a thread produtora (CommandReaderThread);
    * Como o próprio nome fala, a produtora criará as mensagens pela tela do terminal (TerminalView), usando o Scanner nele e rodando na thread principal (ClientThread);
    * A consumidora estará esperando por respostas do Server.
    
3. Como está o projeto na parte do Server:
    * O server (ServerThread) cria uma thread responsável para cada client, cuidando do socket de cada um (ClientHandler);
    * No ClientHandler, é iniciado o processo das filas, onde cada ClientHandler terá uma thread responsável pela Fila1 (FirstQueueThread), e também passará a mensagem para o Fila1 (que se encontra no Clienthandler e FirstQueueThread);
    * Desta forma, todo ClientHandler passará a mensagem obtida pelo para o FirstQueueThread para a Fila2.
    * Em seguida, a passagem é passada no SecondThirdQueueThread.java, onde é responsável pela Fila2 que passará novas mensagens cópias para o Fila3 e Fila4;
    * A cada uma destas filas é controlada pelo seus respectivos arquivos (DatabaseProcessingThread e LogThread).
    * As duas últimas threads passarão as mensagens e suas respectivas BlockingQueue internas para a thread principal do server (ServerThread), onde fará o que foi pedido (usar o BD em memória e criar o log no arquivo).
    
4. Testes
    * Os testes podem ser executados com o Gradle;
    * Infelizmente, o último teste de concorrência não está 100% funcional, porém a concorrência na aplicação direta está funcional. Foram mais problemas com as streams de comunicação de envio (Input e Output) entre Client e Server;
    * OBS: O arquivo de log será deletado no fim dos testes!!!! Se for testar a funcionalidade do Log na execução normal do aplicativo no terminal, faça com que o Gradle ignore os testes.
    
4. Pedido de desculpas
    * Professor, nos desculpe pelo atraso da entrega, mas as intenções eram boas. Queria deixar o projeto 100% com os testes funcionais (ele já funciona bem), e ainda deixarei. Só ocorreu o problema das threads de concorrência com as stream, mas corrigirei logo.
    * Também peço que leve em consideração a determinação em fazer um projeto bem feito sobre o tempo de envio, para ter uma punição menos severa em relação aos pontos.
    
    No mais, todos nós agradecemos a você, professor.
    
    Obrigado!
    
    * Arthur
    * Carlos Humberto
    * Raniel
    * Silvano