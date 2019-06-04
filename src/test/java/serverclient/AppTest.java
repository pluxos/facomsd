package serverclient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import serverclient.client.ClientThreadTest;
import serverclient.constants.StringsConstants;
import serverclient.model.MessageOld;
import serverclient.server.database.LogFile;
import serverclient.server.database.MemoryDB;
import serverclient.server.threads.ServerThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    private final static Logger LOGGER = Logger.getLogger(AppTest.class.getName());

    private static ServerThread serverThread;

    private static ClientThreadTest clientThreadTest;
    private static ExecutorService executor;

    private static volatile BlockingQueue<MessageOld> enviarMensagens = new LinkedBlockingDeque<>();
    private static volatile BlockingQueue<MessageOld> receberRespostas = new LinkedBlockingDeque<>();

    // -----------------------------------------------------------------------------------------------------------------

    /* STARTER */

    @BeforeAll
    synchronized static void beginServer() {

        serverThread = new ServerThread(8080);

        executor = Executors.newFixedThreadPool(2);

        MemoryDB.getInstance();

        executor.execute(serverThread);

        clientThreadTest = new ClientThreadTest(enviarMensagens, receberRespostas);
        executor.execute(clientThreadTest);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /* TEST STARTERS */

    @Disabled
    @Test
    @Order(1)
    void crudOK() {
        /* Teste responsável para verificar se o CRUD do server retornar as mensagens corretas,
           caso não tenha ocorrido erros */

        this.crudOKBody(1, enviarMensagens, receberRespostas);
    }

    @Disabled
    @Test
    @Order(2)
    void crudNOK() {
        /* Teste responsável para verificar se o CRUD do server retornar as mensagens corretas,
           caso tenha ocorrido algum erro */

        this.crudNOKBody(1, enviarMensagens, receberRespostas);
    }

    @Disabled
    @Test
    @Order(3)
    void stateRecovery() {
        /* Teste responsável para verificar se o Server recupera o seu Banco de Dados em memória, através do Log */

        List<MessageOld> listaDeItens = this.stateRecoveryBodyPart1(1, enviarMensagens, receberRespostas);
        this.stopServerAndClients(executor);
        this.restartServerAndClients();
        this.stateRecoveryBodyPart2(1, listaDeItens, enviarMensagens, receberRespostas);
    }

    @Disabled
    @Test
    @Order(4)
    void executionOrder() {
        /* Teste responsável para verificar se o Server está adicionando as mensagens na ordem correta */

        this.executionOrderBody(1, 1000, enviarMensagens, receberRespostas);
    }

    //@Disabled
    @Test
    @Order(5)
    void concurrency10Threads() {
        /* Teste responsável de reexecutar todos os testes anteriores, paralelamente, com 10 clientes,
        com chaves diferentes e verificar que os resultados snão se alteram. */

        //this.stopServerAndClients(executor);

        List<ClientThreadTest> clients = new ArrayList<>();

        ExecutorService executorClients = Executors.newFixedThreadPool(10);

        //this.restartServer(executorClients);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Create and execute client threads
        for (int nThreads = 0; nThreads < 10; nThreads++) {
            clients.add(new ClientThreadTest(new LinkedBlockingDeque<>(), new LinkedBlockingDeque<>()));
            executorClients.submit(clients.get(nThreads));
        }

        // Test 1
        for (int nThreads = 0; nThreads < 10; nThreads++) {
            this.crudOKBody(nThreads + 1,
                    clients.get(nThreads).getNovasMensagensQueue(),
                    clients.get(nThreads).getNovasRespostasQueue());
        }

        // Test 2
        for (int nThreads = 0; nThreads < 10; nThreads++) {
            this.crudNOKBody(nThreads + 1,
                    clients.get(nThreads).getNovasMensagensQueue(),
                    clients.get(nThreads).getNovasRespostasQueue());
        }

        // Test 3
        List<List<MessageOld>> listaDeListaDeItens = new ArrayList<>();
        for (int nThreads = 0; nThreads < 10; nThreads++) {
            listaDeListaDeItens.add(this.stateRecoveryBodyPart1(nThreads * 10,
                    clients.get(nThreads).getNovasMensagensQueue(),
                    clients.get(nThreads).getNovasRespostasQueue()));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.stopServerAndClients(executor);
        this.stopServerAndClients(executorClients);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorClients = this.restartServerAndClients(executorClients, clients);

        for (int nThreads = 0; nThreads < 10; nThreads++) {
            this.stateRecoveryBodyPart2(nThreads * 10,
                    listaDeListaDeItens.get(nThreads),
                    clients.get(nThreads).getNovasMensagensQueue(),
                    clients.get(nThreads).getNovasRespostasQueue());
        }

        // Test 4
        int numInit = 1;
        int limite = 1000;
        for (int nThreads = 0; nThreads < 10; nThreads++) {

            this.executionOrderBody(numInit, limite,
                    clients.get(nThreads).getNovasMensagensQueue(),
                    clients.get(nThreads).getNovasRespostasQueue());

            numInit = limite + 1;
            limite = numInit + 1000;
        }

        this.stopServerAndClients(executorClients);
    }

    @AfterAll
    private static synchronized void cleanDB() {
        MemoryDB.restartDB();
        LogFile.deleteLogFile();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /* UTIL METHODS */

    private MessageOld sendAndReceiveMessage(MessageOld newMessage, BlockingQueue<MessageOld> enviarMensagens,
                                             BlockingQueue<MessageOld> receberRespostas) {
        try {
            enviarMensagens.put(newMessage);
            LOGGER.info("Mensagem " + newMessage.getMessage() + " enviada.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MessageOld resposta = null;
        try {
            LOGGER.info("Resposta da mensagem " + newMessage.getMessage());
            resposta = receberRespostas.take();
            LOGGER.info("Resposta " + resposta.getMessage() + " recebida.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resposta;
    }

    private synchronized void stopServerAndClients(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private synchronized ExecutorService restartServerAndClients(ExecutorService executorClients,
                                                                 List<ClientThreadTest> listClients) {


        serverThread = new ServerThread(8080);

        executor = Executors.newSingleThreadExecutor();
        executorClients = Executors.newFixedThreadPool(10);

        MemoryDB.getInstance();

        executor.execute(serverThread);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int n = 0; n < 10; n++) {
            listClients.set(n, new ClientThreadTest(new LinkedBlockingDeque<>(), new LinkedBlockingDeque<>()));
            executorClients.execute(listClients.get(0));
        }

        return executorClients;
    }

    private synchronized void restartServerAndClients() {
        // RESTART SERVER & CLIENT
        enviarMensagens = new LinkedBlockingDeque<>();
        receberRespostas = new LinkedBlockingDeque<>();
        beginServer();
    }

    private synchronized void restartServer(ExecutorService executorClients) {
        serverThread = new ServerThread(8080);

//        executorClients = Executors.newSingleThreadExecutor();

        MemoryDB.getInstance();

        executorClients.submit(serverThread);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /* BODY BLOCKS FOR THE TESTS */
    private void crudOKBody(int n, BlockingQueue<MessageOld> enviarMensagens, BlockingQueue<MessageOld> receberRespostas) {
        // CREATE
        MessageOld respostaCreate = this.etapa1CrudOk(n, enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_CREATION_SUCCESS_ID.toString() + respostaCreate.getId()
                + " -- OK" + n, respostaCreate.getMessage());


        // READ
        MessageOld messageRead = this.etapa2CrudOk(respostaCreate.getId(), enviarMensagens, receberRespostas);
        assertEquals("OK" + n, messageRead.getMessage());


        // UPDATE
        MessageOld respostaUpdate = this.etapa3CrudOk(n, respostaCreate.getId(), enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_UPDATE_SUCCESS.toString(), respostaUpdate.getMessage());


        // DELETE
        MessageOld respostaDelete = this.etapa4CrudOk(respostaCreate.getId(), enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_DELETE_SUCCESS.toString(), respostaDelete.getMessage());
    }

    private void crudNOKBody(int n, BlockingQueue<MessageOld> enviarMensagens, BlockingQueue<MessageOld> receberRespostas) {
        this.cleanDBCrudNOK(n, enviarMensagens, receberRespostas);

        // CREATE NEW ITEM I
        MessageOld respostaCreate = this.etapa1And2CrudNOK(n, enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_CREATION_SUCCESS_ID.toString() + (respostaCreate.getId())
                + " -- NOK" + n, respostaCreate.getMessage());


        // CREATE NEW ITEM EQUALS TO ITEM I
        MessageOld respostaCreateEq = this.etapa1And2CrudNOK(n, enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.ERR_EXISTENT_MESSAGE.toString(), respostaCreateEq.getMessage());


        // READ NON EXISTENT ITEM J
        MessageOld respostaRead = this.etapa3CrudNOK(respostaCreate.getId() + 2, enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.ERR_NON_EXISTENT_ID.toString(), respostaRead.getMessage());


        // UPDATE NON EXISTENT ITEM J
        MessageOld respostaUpdateNOKitemJ = this.etapa4CrudNOK(n, -1, enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.ERR_NON_EXISTENT_ID.toString(), respostaUpdateNOKitemJ.getMessage());


        // READ NEW CREATE-UPDATE ITEM J
        String textoUpdateItemJ = "NOK-OK" + n + "Upgrade";
        MessageOld respostaReadItemJ = this.etapa5CrudNOK(n, enviarMensagens, receberRespostas);
        assertEquals(textoUpdateItemJ, respostaReadItemJ.getMessage());


        // DELETE ITEM J & ITEM I
        MessageOld respostaDelete = this.deleteMessagesDBCrudNOK(respostaReadItemJ.getId(),
                enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_DELETE_SUCCESS.toString(), respostaDelete.getMessage());
        MessageOld respostaDelete2 = this.deleteMessagesDBCrudNOK(respostaCreate.getId(),
                enviarMensagens, receberRespostas);
        assertEquals(StringsConstants.MESSAGE_DELETE_SUCCESS.toString(), respostaDelete2.getMessage());
    }

    private List<MessageOld> stateRecoveryBodyPart1(int n, BlockingQueue<MessageOld> enviarMensagens,
                                                    BlockingQueue<MessageOld> receberRespostas) {
        // CREATE 5 ITEMS
        List<MessageOld> listaDeItens = this.etapa1StateRecovery(n, enviarMensagens, receberRespostas);

//        // KILL SERVER (AND CLIENT)
//        this.stopServerAndClients(executor);
//
//        // RESTART SERVER & CLIENT
//        this.etapa3StateRecovery();

        return listaDeItens;
    }

    private void stateRecoveryBodyPart2(int n, List<MessageOld> listaDeItens, BlockingQueue<MessageOld> enviarMensagens,
                                        BlockingQueue<MessageOld> receberRespostas) {
        // READ LASTLY CREATED ITEMS
        List<MessageOld> listaDeItensRecuperados = this.etapa4StateRecovery(listaDeItens, n);
        assertEquals(listaDeItens, listaDeItensRecuperados);

        // DELETE ALL ITEMS
        List<MessageOld> deletedItems = this.deleteMessagesDBStateRecovery(listaDeItensRecuperados);
        for (MessageOld msg : deletedItems) {
            assertEquals(StringsConstants.MESSAGE_DELETE_SUCCESS.toString(), msg.getMessage());
        }
    }

    private void executionOrderBody(int numInicial, int quantidade, BlockingQueue<MessageOld> enviarMensagens,
                                    BlockingQueue<MessageOld> receberRespostas) {
        // CREATE (quantidade) ITEMS with (numInicial) values as messages

        List<MessageOld> listMessages = this.etapa1ExecutionOrder(numInicial, quantidade);

        assertEquals(quantidade + 1,
                Integer.parseInt((listMessages.get(listMessages.size() - 1)).getMessage()));

        this.deleteMessagesDBExecutionOrder(listMessages);
    }


    // -----------------------------------------------------------------------------------------------------------------

    /* TEST INTERNAL FUNCTIONS */

    /* CRUDOK */
    private MessageOld etapa1CrudOk(int num, BlockingQueue<MessageOld> enviarMensagens,
                                    BlockingQueue<MessageOld> receberRespostas) {
        // CREATE
        String texto = "OK" + num;
        MessageOld messageCreate = new MessageOld(1, texto);
        MessageOld resposta = this.sendAndReceiveMessage(messageCreate, enviarMensagens, receberRespostas);
        return resposta;
    }

    private MessageOld etapa2CrudOk(long id, BlockingQueue<MessageOld> enviarMensagens,
                                    BlockingQueue<MessageOld> receberRespostas) {
        // READ
        MessageOld messageRead = new MessageOld(2, id);
        MessageOld respostaRead = this.sendAndReceiveMessage(messageRead, enviarMensagens, receberRespostas);
        return respostaRead;
    }

    private MessageOld etapa3CrudOk(int num, long id, BlockingQueue<MessageOld> enviarMensagens,
                                    BlockingQueue<MessageOld> receberRespostas) {
        // UPDATE
        String textoUpdate = "OK" + num + "Upgrade";
        MessageOld messageUpdate = new MessageOld(3, id, textoUpdate);
        MessageOld respostaUpdate = this.sendAndReceiveMessage(messageUpdate, enviarMensagens, receberRespostas);
        return respostaUpdate;
    }

    private MessageOld etapa4CrudOk(long id, BlockingQueue<MessageOld> enviarMensagens,
                                    BlockingQueue<MessageOld> receberRespostas) {
        // DELETE
        MessageOld messageDelete = new MessageOld(4, id);
        MessageOld respostaDelete = this.sendAndReceiveMessage(messageDelete, enviarMensagens, receberRespostas);
        return respostaDelete;
    }




    /* CRUDNOK */
    private void cleanDBCrudNOK(int num, BlockingQueue<MessageOld> enviarMensagens,
                                BlockingQueue<MessageOld> receberRespostas) {
        // DELETE ITEM I IF IT ALREADY EXISTS
        if (MemoryDB.getDatabase().containsValue("NOK" + num)) {
            MemoryDB.getDatabase().values().remove("NOK" + num);
        }
    }

    private MessageOld etapa1And2CrudNOK(int num, BlockingQueue<MessageOld> enviarMensagens,
                                         BlockingQueue<MessageOld> receberRespostas) {
        // 1º - CREATE NEW ITEM I
        // 2º - CREATE NEW ITEM EQUALS TO ITEM I
        String textoItemI = "NOK" + num;
        MessageOld messageCreate = new MessageOld(1, textoItemI);
        MessageOld respostaCreate = this.sendAndReceiveMessage(messageCreate, enviarMensagens, receberRespostas);
        return respostaCreate;
    }

    private MessageOld etapa3CrudNOK(long id, BlockingQueue<MessageOld> enviarMensagens,
                                     BlockingQueue<MessageOld> receberRespostas) {
        // READ NON EXISTENT ITEM J
        MessageOld messageRead = new MessageOld(2, id);
        MessageOld respostaRead = this.sendAndReceiveMessage(messageRead, enviarMensagens, receberRespostas);
        return respostaRead;
    }

    private MessageOld etapa4CrudNOK(int num, long id, BlockingQueue<MessageOld> enviarMensagens,
                                     BlockingQueue<MessageOld> receberRespostas) {
        // UPDATE NON EXISTENT ITEM J
        String textoUpdateItemJ = "NOK" + num + "Upgrade";
        MessageOld messageUpdateNOKitemJ = new MessageOld(3, id, textoUpdateItemJ);
        MessageOld respostaUpdateNOKitemJ = this.sendAndReceiveMessage(messageUpdateNOKitemJ,
                enviarMensagens, receberRespostas);
        return respostaUpdateNOKitemJ;
    }

    private MessageOld etapa5CrudNOK(int num, BlockingQueue<MessageOld> enviarMensagens,
                                     BlockingQueue<MessageOld> receberRespostas) {
        // READ NEW CREATE-UPDATE ITEM J
        String textoItemJ = "NOK-OK" + num;
        String textoUpdateItemJ = "NOK-OK" + num + "Upgrade";
        MessageOld messageCreateItemJ = new MessageOld(1, textoItemJ);
        MessageOld msgCreateAnswer = this.sendAndReceiveMessage(messageCreateItemJ, enviarMensagens, receberRespostas);
        MessageOld messageUpdateItemJ = new MessageOld(3, msgCreateAnswer.getId(), textoUpdateItemJ);
        MessageOld msgUpdateAnswer = this.sendAndReceiveMessage(messageUpdateItemJ, enviarMensagens, receberRespostas);
        MessageOld messageReadItemJ = new MessageOld(2, msgUpdateAnswer.getId());
        MessageOld respostaReadItemJ = this.sendAndReceiveMessage(messageReadItemJ, enviarMensagens, receberRespostas);
        return respostaReadItemJ;
    }

    private MessageOld deleteMessagesDBCrudNOK(long id, BlockingQueue<MessageOld> enviarMensagens,
                                               BlockingQueue<MessageOld> receberRespostas) {
        // DELETE ITEM J & ITEM I
        MessageOld messageDelete = new MessageOld(4, id);
        MessageOld respostaDelete = this.sendAndReceiveMessage(messageDelete, enviarMensagens, receberRespostas);
        return respostaDelete;
    }




    /* StateRecovery */
    private List<MessageOld> etapa1StateRecovery(int num, BlockingQueue<MessageOld> enviarMensagens,
                                                 BlockingQueue<MessageOld> receberRespostas) {
        // CREATE 5 ITEMS
        List<MessageOld> listItems = new ArrayList<>();
        for (int i = num; i <= num + 4; i++) {
            MessageOld messageCreate = new MessageOld(1, "Item" + i);
            MessageOld answer = this.sendAndReceiveMessage(messageCreate, enviarMensagens, receberRespostas);
            messageCreate.setId(answer.getId());
            listItems.add(messageCreate);
        }

        return listItems;
    }

    private List<MessageOld> etapa4StateRecovery(List<MessageOld> listDeItens, int num) {
        // READ LASTLY CREATED ITEMS
        List<MessageOld> listaItemsRecuperados = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MessageOld messageCreateRead = new MessageOld(2, listDeItens.get(i).getId());
            MessageOld answer = this.sendAndReceiveMessage(messageCreateRead, enviarMensagens, receberRespostas);
            listaItemsRecuperados.add(answer);
        }

        return listaItemsRecuperados;
    }

    private List<MessageOld> deleteMessagesDBStateRecovery(List<MessageOld> listaDeItens) {
        // DELETE ITEMS
        List<MessageOld> deletedItens = new ArrayList<>();
        for (MessageOld msg : listaDeItens) {
            MessageOld messageDelete = new MessageOld(4, msg.getId());
            MessageOld respostaDelete = this.sendAndReceiveMessage(messageDelete, enviarMensagens, receberRespostas);
            deletedItens.add(respostaDelete);
        }

        return deletedItens;
    }




    /* Execution Order */
    private List<MessageOld> etapa1ExecutionOrder(int numInicial, int quantidade) {
        // CREATE 1000 ITEMS with N values as messages
        List<MessageOld> listOfMessages = new ArrayList<>();

        MessageOld firstMessage = new MessageOld(1, Integer.toString(numInicial));
        firstMessage = this.sendAndReceiveMessage(firstMessage, enviarMensagens, receberRespostas);
        MessageOld msgRead = new MessageOld(2, firstMessage.getId());
        msgRead = this.sendAndReceiveMessage(msgRead, enviarMensagens, receberRespostas);
        listOfMessages.add(msgRead);

        while (Integer.parseInt(msgRead.getMessage()) <= quantidade) {

            MessageOld nextMessage = new MessageOld(1, String.valueOf(Integer.parseInt(msgRead.getMessage()) + 1));
            nextMessage = this.sendAndReceiveMessage(nextMessage, enviarMensagens, receberRespostas);
            msgRead = new MessageOld(2, nextMessage.getId());
            msgRead = this.sendAndReceiveMessage(msgRead, enviarMensagens, receberRespostas);
            listOfMessages.add(msgRead);

        }

        return listOfMessages;
    }

    private void deleteMessagesDBExecutionOrder(List<MessageOld> listOfMessages) {
        // DELETE LAST THOUSAND OBJECTS
        // WORKS ONLY WITH JUNIT, BECAUSE OF ASSERTEQUALS
        for (MessageOld msg : listOfMessages) {
            MessageOld msgDelete = new MessageOld(4, msg.getId());
            assertEquals(StringsConstants.MESSAGE_DELETE_SUCCESS.toString(),
                    this.sendAndReceiveMessage(msgDelete, enviarMensagens, receberRespostas).getMessage());
        }
    }

}