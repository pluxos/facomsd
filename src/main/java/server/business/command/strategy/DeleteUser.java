package server.business.command.strategy;

import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class DeleteUser implements CommandStrategy {

	@Override
	public GenericResponse executeCommand(GenericCommand inputParams) {
		BigInteger code = inputParams.getCode();
		GenericResponse genericResponse = new GenericResponse();

		if(Manipulator.getValue(code) != null) {
			Manipulator.removeValue(code);

			if (!Manipulator.containKey(code)){
				genericResponse.setMsg("Deletado com sucesso");
			} else {
				genericResponse.setMsg("Erro ao deletar");
			}
		} else {
			genericResponse.setMsg("Erro: Usuário não existe");
		}

		return genericResponse;
	}
}