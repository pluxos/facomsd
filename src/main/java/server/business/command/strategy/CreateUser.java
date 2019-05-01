package server.business.command.strategy;

public class CreateUser implements RequestStrategy {

	@Override
	public boolean executeCommand(String[] inputParams) {
		System.out.println("Create USER");
		return true;
	}
}