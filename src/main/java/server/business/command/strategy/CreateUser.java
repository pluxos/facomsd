package server.business.command.strategy;

import server.commons.domain.GenericCommand;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class CreateUser implements CommandStrategy {

	@Override
	public boolean executeCommand(GenericCommand inputParams) {
		BigInteger code = inputParams.getCode();
		byte[] data = inputParams.getData();

		Manipulator.addValue(code, data);

		return true;
	}
}