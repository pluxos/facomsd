package server.business.command.strategy;

public class DeleteUser implements RequestStrategy {

	@Override
	public boolean executeCommand(String[] inputParams) {
		System.out.println("DELETE USER");
		return true;
	}
}