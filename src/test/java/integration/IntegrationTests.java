package integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import client.ClientApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTests {
	
	@AfterClass
	public static void init() throws IOException {
		setBaseLog();
	}
	
	@After
	public void after() {
		printDivision();
	}
	
	@Test
	public void test1_serverShouldLoadLogFile() throws FileNotFoundException {
		System.out.println("TEST 1: LOADING LOG FILE AFTER SHUTDOWN / GETING USER BASED ON LOG\n");
		String[] args = {"teste", "src/test/resources/get.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test2_serverShouldCreate() {
		System.out.println("TEST 2: CREATING NEW USER\n");
		String[] args = {"teste", "src/test/resources/create.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test3_serverShouldUpdate() {
		System.out.println("TEST 3: UPDATING EXISTING USER\n");
		String[] args = {"teste", "src/test/resources/update.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test4_serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId() {
		System.out.println("TEST 4: FAILING TO CREATE USER WITH EXISTING ID\n");
		String[] args = {"teste", "src/test/resources/create.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test5_serverShouldDelete() {
		System.out.println("TEST 5: DELETING EXISTING USER\n");
		String[] args = {"teste", "src/test/resources/delete.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test6_serverShouldThrowErrorWhenUpdatingInexistentId() {
		System.out.println("TEST 6: FAILING TO UPDATE USER WITH INEXISTENT ID\n");
		String[] args = {"teste", "src/test/resources/update.txt"};
		ClientApplication.main(args);
	}
	
	@Test
	public void test7_serverShouldThrowErrorWhenDeletingInexistentId() {
		System.out.println("TEST 7: FAILING TO DELETE USER WITH INEXISTENT ID\n");
		String[] args = {"teste", "src/test/resources/delete.txt"};
		ClientApplication.main(args);
	}
	
	/*@Test
	public void serverShouldAcceptMultipleClients() throws IOException {
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldAcceptMultipleCommands() throws IOException {
		PrintWriter pw = new PrintWriter("log4j/log4j-application.log");
		pw.close();
	}*/
	
	private void printDivision() {
		System.out.println("-------------------------------------------------------------");
	}
	
	private static void setBaseLog() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("comand.log");
		writer.println("{\"method\":\"CREATE\",\"code\":3224115,\"data\":\"rO0ABXNyABpjbGllbnQuY29tbW9ucy5kb21haW4uVXNlcgAAAAAAAAABAgADTAAFZW1haWx0ABJMamF2YS9sYW5nL1N0cmluZztMAARuYW1lcQB+AAFMAAhwYXNzd29yZHEAfgABeHB0ABFtYXRoZXVzQGdtYWlsLmNvbXQAB21hdGhldXN0AAYxMjNhc2Q=\"}");
		writer.close();
	}
}