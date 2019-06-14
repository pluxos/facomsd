package integration;

import client.controller.Client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IntegrationTests {

    private static ManagedChannel stub;
	
    public static void main(String[] args) {
        stub = ManagedChannelBuilder.forAddress(
                args[0], Integer.parseInt(args[1])
            ).executor(Executors.newFixedThreadPool(1)).usePlaintext().build();
    	runIntegrationTests();
    }
    
    private static void runIntegrationTests() {
    	test1_serverShouldLoadLogFile();
    	test2_serverShouldCreate();
    	test3_serverShouldUpdate();
    	test4_serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId();
    	test5_serverShouldDelete();
    	test6_serverShouldThrowErrorWhenUpdatingInexistentId();
    	test7_serverShouldThrowErrorWhenDeletingInexistentId();
    	test8_shouldAcceptMultipleRequestsFromMultipleClients();
    	test9_shouldGuaranteeCorrectnessFromPreviousRequests();
    	System.exit(0);
    }
    
    private static void afterMethod() {
    	printDivision();
    }

    private static void test1_serverShouldLoadLogFile() {
    	System.out.println("TEST 1: LOADING LOG FILE AFTER SHUTDOWN / GETTING USER BASED ON LOG");
    	String[] args = {"", "", "teste", "src/test/resources/get.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test2_serverShouldCreate() {
    	System.out.println("TEST 2: CREATING NEW USER");
    	String[] args = {"","","teste", "src/test/resources/create.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test3_serverShouldUpdate() {
    	System.out.println("TEST 3: UPDATING EXISTING USER");
    	String[] args = {"","","teste", "src/test/resources/update.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test4_serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId() {
    	System.out.println("TEST 4: FAILING TO CREATE USER WITH EXISTING ID");
    	String[] args = {"","","teste", "src/test/resources/create.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test5_serverShouldDelete() {
    	System.out.println("TEST 5: DELETING EXISTING USER");
    	String[] args = {"","","teste", "src/test/resources/delete.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test6_serverShouldThrowErrorWhenUpdatingInexistentId() {
    	System.out.println("TEST 6: FAILING TO UPDATE USER WITH INEXISTENT ID");
    	String[] args = {"","","teste", "src/test/resources/update.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }

    private static void test7_serverShouldThrowErrorWhenDeletingInexistentId() {
    	System.out.println("TEST 7: FAILING TO DELETE USER WITH INEXISTENT ID");
    	String[] args = {"","","teste", "src/test/resources/delete.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }
    
    private static void test8_shouldAcceptMultipleRequestsFromMultipleClients() {
    	System.out.println("TEST 8: EXECUTING MULTIPLE COMMANDS FROM MULTIPLE PARALELL CLIENTS");
    	String[][] clientArgs = {
    			{"","","teste", "src/test/resources/stress1.txt"},
    			{"","","teste", "src/test/resources/stress2.txt"},
    			{"","","teste", "src/test/resources/stress3.txt"},
    			{"","","teste", "src/test/resources/stress4.txt"}};
    	ExecutorService pool = Executors.newFixedThreadPool(4);
    	for (int i = 0; i < 4; i++) {
    		pool.execute(new Client(clientArgs[i], stub));
    	}
    	pool.shutdown();
    	try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	afterMethod();
    }
    
    private static void test9_shouldGuaranteeCorrectnessFromPreviousRequests() {
    	System.out.println("TEST 9: RUNNING INTEGRATION VERIFIER\n"
    			+ "- ID's ENDING IN 0 SHOULD HAVE BEEN DELETED\n"
    			+ "- ID's ENDING IN 5 HOULD HAVE BEEN UPDATED (ALL UPDATED USERS HAVE THEIR NAMES UPDATED)");
    	String[] args = {"","", "teste", "src/test/resources/verify_integrity.txt"};
    	new Client(args, stub).run();
    	afterMethod();
    }
    
    private static void printDivision() {
		System.out.println("-------------------------------------------------------------");
	}
}