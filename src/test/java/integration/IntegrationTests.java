package integration;

import java.io.FileNotFoundException;

import client.controller.Client;

public class IntegrationTests {
	
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /*System.out.println("Bem vindo ao cliente de teste!");

        ExecutorService pool = Executors.newFixedThreadPool(10);

        String[] clientArgs = {"teste", "src/test/resources/stress.txt"};

        for (int i = 0; i < 10; i ++) {
            pool.execute(new Client(clientArgs));
        }*/
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
    
    private static void printDivision() {
		System.out.println("-------------------------------------------------------------");
	}
}