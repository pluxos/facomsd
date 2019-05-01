package server.business.command.strategy;

public class GetUser implements RequestStrategy {

	@Override
	public boolean executeCommand(String[] inputParams) {
		System.out.println("GET USER");
		return true;
	}
}