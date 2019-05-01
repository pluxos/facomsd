package server.business.command.strategy;

import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class GetUser implements CommandStrategy {

	@Override
	public GenericResponse executeCommand(GenericCommand inputParams) {
		BigInteger code = inputParams.getCode();
		GenericResponse genericResponse = new GenericResponse();

		byte[] res = Manipulator.getValue(code);

		if(res != null){
			genericResponse.setData(res);
		} else {
			genericResponse.setMsg("Erro ao realizar GET");
		}

		return genericResponse;
	}
}