package server.business.command.strategy;

public class UpdateUser implements RequestStrategy {
	
	@Override
	public boolean executeCommand(String[] inputParams) {
		System.out.println("UPDATE USER");
		return true;
	}
}