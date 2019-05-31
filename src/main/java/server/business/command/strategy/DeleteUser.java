package server.business.command.strategy;

import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import server.commons.domain.GenericCommand;
import server.commons.utils.DataCodificator;
import server.commons.utils.MessageMap;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;

public class DeleteUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();

		GenericResponse deleteResponse;

		if(Manipulator.getValue(code) != null) {
			Manipulator.removeValue(code);

			if (!Manipulator.containKey(code)) {
				deleteResponse = GenericResponse.newBuilder()
						.setStatus(MessageMap.SUCCESS.getMessage())
						.setMessage(MessageMap.DELETE_SUCCESS.getMessage())
						.build();
			} else {
				deleteResponse = GenericResponse.newBuilder()
						.setStatus(MessageMap.ERROR.getMessage())
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.build();
			}
		} else {
			deleteResponse = GenericResponse.newBuilder()
					.setStatus(MessageMap.ERROR.getMessage())
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.build();
		}

		if(genericCommand.getOutput() != null) {
			genericCommand.getOutput().onNext(deleteResponse);
			genericCommand.getOutput().onCompleted();
		}
	}
}