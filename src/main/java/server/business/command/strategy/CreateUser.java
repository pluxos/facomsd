package server.business.command.strategy;

import io.grpc.GenericResponse;
import server.commons.domain.GenericCommand;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class CreateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = genericCommand.getData();

		GenericResponse createResponse;

		if (Manipulator.getValue(code) == null) {
			Manipulator.addValue(code, data);

			if (Manipulator.containKey(code)) {
				createResponse = GenericResponse.newBuilder()
						.setMessage(MessageMap.CREATE_SUCCESS.getMessage())
						.setStatus(MessageMap.SUCCESS.getMessage())
						.build();
			} else {
				createResponse = GenericResponse.newBuilder()
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.setStatus(MessageMap.ERROR.getMessage())
						.build();
			}
		} else {
			createResponse = GenericResponse.newBuilder()
					.setMessage(MessageMap.USER_EXISTS.getMessage())
					.setStatus(MessageMap.ERROR.getMessage())
					.build();
		}

		genericCommand.getOutput().onNext(createResponse);
		genericCommand.getOutput().onCompleted();
	}
}