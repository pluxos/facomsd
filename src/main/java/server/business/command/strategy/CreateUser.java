package server.business.command.strategy;

import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class CreateUser implements CommandStrategy {

	@Override
	public GenericResponse executeCommand(GenericCommand inputParams) {
		BigInteger code = inputParams.getCode();
		byte[] data = inputParams.getData();
		GenericResponse genericResponse = new GenericResponse();

		Manipulator.addValue(code, data);

		if (Manipulator.containKey(code)) {
			genericResponse.setMsg("Criado com sucesso");
		} else {
			genericResponse.setMsg("Erro ao criar usuário");
		}

		return genericResponse;
	}
}