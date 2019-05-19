package server.business.command.strategy;

import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class UpdateUser implements CommandStrategy {

	@Override
	public GenericResponse executeCommand(GenericCommand inputParams) {
		BigInteger code = inputParams.getCode();
		byte[] data = inputParams.getData();
		GenericResponse genericResponse = new GenericResponse();

		if(Manipulator.getValue(code) != null) {
			Manipulator.updateValue(code, data);

			if (Manipulator.containKey(code)) {
				genericResponse.setMsg("Atualizado com sucesso");
			} else {
				genericResponse.setMsg("Erro ao atualizar usuário");
			}
		} else {
			genericResponse.setMsg("Erro: Usuário não existe");
		}

		return genericResponse;
	}
}