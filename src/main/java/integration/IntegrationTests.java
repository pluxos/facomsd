package integration;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import client.controller.Client;

public class IntegrationTests {
	
    /*public static void main(String[] args) throws FileNotFoundException, InterruptedException {
    	runIntegrationTests();
    }
    
    public static void runIntegrationTests() throws FileNotFoundException, InterruptedException {
    	test1_serverShouldLoadLogFile();
    	test2_serverShouldCreate();
    	test3_serverShouldUpdate();
    	test4_serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId();
    	test5_serverShouldDelete();
    	test6_serverShouldThrowErrorWhenUpdatingInexistentId();
    	test7_serverShouldThrowErrorWhenDeletingInexistentId();
    	test8_shouldAcceptMultipleRequestsFromMultipleClients();
    	test9_shouldGuaranteeCorrectnessFromPreviousRequests();
    }
    
    public static void afterMethod() {
    	printDivision();
    }

    public static void test1_serverShouldLoadLogFile() throws FileNotFoundException, InterruptedException {
    	System.out.println("TEST 1: LOADING LOG FILE AFTER SHUTDOWN / GETTING USER BASED ON LOG\n");
    	String[] args = {"teste", "src/test/resources/get.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test2_serverShouldCreate() {
    	System.out.println("TEST 2: CREATING NEW USER\n");
    	String[] args = {"teste", "src/test/resources/create.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test3_serverShouldUpdate() {
    	System.out.println("TEST 3: UPDATING EXISTING USER\n");
    	String[] args = {"teste", "src/test/resources/update.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test4_serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId() {
    	System.out.println("TEST 4: FAILING TO CREATE USER WITH EXISTING ID\n");
    	String[] args = {"teste", "src/test/resources/create.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test5_serverShouldDelete() {
    	System.out.println("TEST 5: DELETING EXISTING USER\n");
    	String[] args = {"teste", "src/test/resources/delete.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test6_serverShouldThrowErrorWhenUpdatingInexistentId() {
    	System.out.println("TEST 6: FAILING TO UPDATE USER WITH INEXISTENT ID\n");
    	String[] args = {"teste", "src/test/resources/update.txt"};
    	new Client(args).run();
    	afterMethod();
    }

    public static void test7_serverShouldThrowErrorWhenDeletingInexistentId() throws FileNotFoundException {
    	System.out.println("TEST 7: FAILING TO DELETE USER WITH INEXISTENT ID\n");
    	String[] args = {"teste", "src/test/resources/delete.txt"};
    	new Client(args).run();
    	afterMethod();
    }
    
    public static void test8_shouldAcceptMultipleRequestsFromMultipleClients() {
    	System.out.println("TEST 8: EXECUTING MULTIPLE COMMANDS FROM MULTIPLE PARALELL CLIENTS");
    	String[][] clientArgs = {
    			{"teste", "src/test/resources/stress1.txt"},
    			{"teste", "src/test/resources/stress2.txt"},
    			{"teste", "src/test/resources/stress3.txt"},
    			{"teste", "src/test/resources/stress4.txt"},
    			{"teste", "src/test/resources/stress5.txt"},
    			{"teste", "src/test/resources/stress6.txt"},
    			{"teste", "src/test/resources/stress7.txt"},
    			{"teste", "src/test/resources/stress8.txt"},
    			{"teste", "src/test/resources/stress9.txt"},
    			{"teste", "src/test/resources/stress10.txt"}};
    	ExecutorService pool = Executors.newFixedThreadPool(10);
    	for (int i = 0; i < 10; i++) {
    		pool.execute(new Client(clientArgs[i]));
    	}
    	pool.shutdown();
    	try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	afterMethod();
    }
    
    public static void test9_shouldGuaranteeCorrectnessFromPreviousRequests() throws FileNotFoundException {
    	System.out.println("TEST 9: RUNNING INTEGRATION VERIFIER\n"
    			+ "USERS WITH ID BETWEEN 0 AND 500 WERE CREATED\n"
    			+ "- ID's ENDING IN 0 SHOULD HAVE BEEN DELETED\n"
    			+ "- ID's ENDING IN 5 HOULD HAVE BEEN UPDATED (ALL UPDATED USERS HAVE THEIR NAMES UPDATED)\n");
    	String[] args = {"teste", "src/test/resources/verify_integrity.txt"};
    	new Client(args).run();
    	afterMethod();
    }
    
    private static void printDivision() {
		System.out.println("-------------------------------------------------------------");
	}*/
}