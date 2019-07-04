# Server-Client #

# ATOMIX #

## Sistemas Distribuidos - 2019/1 - Parte 3 ##

1. Compilar e executar o programa : 

    * mvn compile
    * mvn test
    
    SERVERS
    * mvn exec:java -Dexec.mainClass="server_client.server.MessageServer" -Dexec.args="0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
    * mvn exec:java -Dexec.mainClass="server_client.server.MessageServer" -Dexec.args="1 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
    * mvn exec:java -Dexec.mainClass="server_client.server.MessageServer" -Dexec.args="2 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
    
    CLIENTS
    * mvn exec:java -Dexec.mainClass="server_client.client.DistributedMapClient" -Dexec.args="0 0 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
    * mvn exec:java -Dexec.mainClass="server_client.client.DistributedMapClient" -Dexec.args="1 1 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"
    * mvn exec:java -Dexec.mainClass="server_client.client.DistributedMapClient" -Dexec.args="2 2 127.0.0.1 5000 127.0.0.1 5001 127.0.0.1 5002"

2. Como está o projeto na parte do Client:
    * O Client (DistributedMapClient) iniciará sua thread pelo main, em seguida criando um node para si no cluster do Atomix e criando conhecimento dos servers que se encontram nele (member-0. member-1, member-2);
    * Criará um Serializer que reconhecerá o objeto do tipo Message e todos os seus atributos que não são String ou primitivos (no caso, BigInteger);
    * Enviará as mensagens para o server com o método .send() do CommunicationService do Atomix e aguardará um CompletableFuture do server para ser printado (System.out.println);
    
3. Como está o projeto na parte do Server:
    * O server (MessageServer) iniciará sua thread pelo main, em seguida criando um node para si no cluster do Atomix e criando conhecimento dos servers que se encontram nele (member-0. member-1, member-2), assim como feito no Client, porém ele deverá possuir um profile (declarado com o .withProfile() );
    * Em seguida, iniciará o DistributedMap e aguardará uma mensagem do server com o método .subcribe() do CommunicationService;
    * A mensagem será validada pelo MessageService e MessageRepository (a validação foi dividida em duas partes, para organizar mais o código e tentar seguir um padrão);
    * Após validada, ela executará sua ação no DistributedMap e tera o resultado enviado de volta ao Client como CompletableFuture (dentro do .subscribe() );
    
4. Agradecimentos
    * Agradecemos ao professor pelo ensino deste período e pelo aprendiza que obtivemos com todo este trabalho !
    
    Obrigado!
    
    * Arthur
    * Carlos Humberto
    * Raniel
    * Silvano