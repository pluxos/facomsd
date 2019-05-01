package integration;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;

public class IntegrationTests {

	private File fileCommands;
	
	@BeforeClass
	public static void init() {
		
	}
	
	@Before
	public void loadTestDatabase() {
		// load database
		// copy database
		// apply tests on the copy
	}
	
	public void serverShouldAcceptMultipleClients() {
		
	}
	
	public void serverShouldAcceptMultipleCommands() {
		
	}
	
	public void serverShouldThrowErrorWhenCreatingDataWithAlreadyExistingId() {
		
	}
	
	public void serverShouldThrowErrorWhenUpdatingInexistentData() {
		
	}
	
	public void serverShouldThrowErrorWhenDeletingInexistentData() {
		
	}
	
	public void serverShouldNotifyEmptyGet() {
		
	}
	
	public void serverShouldCreate() {
		// create then get
	}
	
	public void serverShouldGet() {
		
	}
	
	public void serverShouldDelete() {
		
	}
	
	public void serverShouldUpdate() {
		
	}
}