package server.business.command.strategy;

import io.grpc.GenericResponse;
import server.commons.domain.GenericCommand;
import server.commons.utils.MessageMap;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;

public class UpdateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = genericCommand.getData();

		GenericResponse updateResponse;

		if(Manipulator.getValue(code) != null) {
			Manipulator.updateValue(code, data);

			if (Manipulator.containKey(code)) {
				updateResponse = GenericResponse.newBuilder()
						.setMessage(MessageMap.UPDATE_SUCCESS.getMessage())
						.setStatus(MessageMap.SUCCESS.getMessage())
						.build();
			} else {
				updateResponse = GenericResponse.newBuilder()
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.setStatus(MessageMap.ERROR.getMessage())
						.build();
			}
		} else {
			updateResponse = GenericResponse.newBuilder()
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.setStatus(MessageMap.ERROR.getMessage())
					.build();
		}

		if(genericCommand.getOutput() != null) {
			genericCommand.getOutput().onNext(updateResponse);
			genericCommand.getOutput().onCompleted();
		}
	}
}