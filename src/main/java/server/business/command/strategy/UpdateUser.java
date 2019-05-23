package server.business.command.strategy;

import io.grpc.UpdateResponse;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class UpdateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = genericCommand.getData();
		UpdateResponse updateResponse;

		if(Manipulator.getValue(code) != null) {
			Manipulator.updateValue(code, data);

			if (Manipulator.containKey(code)) {
				updateResponse = UpdateResponse.newBuilder()
						.setMessage(MessageMap.UPDATE_SUCCESS.getMessage())
						.setStatus(MessageMap.SUCCESS.getMessage())
						.build();
			} else {
				updateResponse = UpdateResponse.newBuilder()
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.setStatus(MessageMap.ERROR.getMessage())
						.build();
			}
		} else {
			updateResponse = UpdateResponse.newBuilder()
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.setStatus(MessageMap.ERROR.getMessage())
					.build();
		}

		genericCommand.getOutput().onNext(updateResponse);
		genericCommand.getOutput().onCompleted();
	}
}