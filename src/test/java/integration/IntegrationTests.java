package integration;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.ClientApplication;

public class IntegrationTests {

	private String commandsFromFile;
	
	@BeforeClass
	public static void init() throws FileNotFoundException {
		// clears log file
		new PrintWriter("log4j/log4j-application.log").close();
		new PrintWriter("src/test/resources/ahah.txt").close();
	}
	
	@Before
	public void loadTestDatabase() {
		// load database
		// copy database
		// apply tests on the copy
	}
	
	@Test
	public void serverShouldCreate() {
		System.setIn(getUserInput("create; 123; matheus@em; 123asd; matheus"));
		System.setIn(getUserInput("get; 123"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldUpdate() {
		System.setIn(getUserInput("update; 123; matheus@em; 123asd; update_name"));
		System.setIn(getUserInput("get; 123"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldDelete() {
		System.setIn(getUserInput("delete; 123"));
		System.setIn(getUserInput("get; 123"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId() {
		System.setIn(getUserInput("create; 123; matheus@em; 123asd; matheus"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldThrowErrorWhenUpdatingInexistentId() {
		System.setIn(getUserInput("update; 456; matheus@em; 123asd; update_name"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldThrowErrorWhenDeletingInexistentId() {
		System.setIn(getUserInput("delete; 456"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldNotifyEmptyGet() {
		System.setIn(getUserInput("get; 456"));
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldAcceptMultipleClients() throws IOException {
		ClientApplication.main(null);
	}
	
	@Test
	public void serverShouldAcceptMultipleCommands() throws IOException {
		PrintWriter pw = new PrintWriter("log4j/log4j-application.log");
		pw.close();
	}
	
	@Test
	public void serverShouldRecoverThroughLogsAfterShutdown() {
		System.setIn(getUserInput("get; 456" + System.lineSeparator() + "get; 123" + System.lineSeparator() + "get; 789"));
		ClientApplication.main(null);
	}
	
	private ByteArrayInputStream getUserInput(String commands) {
		return new ByteArrayInputStream((commands + System.lineSeparator() + "sair").getBytes());
	}
	
	@Test
	public void test2() throws IOException {
		String[] args = {"teste", "persons.txt"};
		ClientApplication.main(args);
	}
}