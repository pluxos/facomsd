package server.business.command.strategy;

import io.grpc.CreateResponse;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class CreateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = genericCommand.getData();
		CreateResponse createResponse;

		if (Manipulator.getValue(code) == null) {
			Manipulator.addValue(code, data);

			if (Manipulator.containKey(code)) {
				createResponse = CreateResponse.newBuilder()
						.setMessage(MessageMap.CREATE_SUCCESS.getMessage())
						.setStatus(MessageMap.SUCCESS.getMessage())
						.build();
			} else {
				createResponse = CreateResponse.newBuilder()
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.setStatus(MessageMap.ERROR.getMessage())
						.build();
			}
		} else {
			createResponse = CreateResponse.newBuilder()
					.setMessage(MessageMap.USER_EXISTS.getMessage())
					.setStatus(MessageMap.ERROR.getMessage())
					.build();
		}

		genericCommand.getOutput().onNext(createResponse);
		genericCommand.getOutput().onCompleted();
	}
}