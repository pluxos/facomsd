package server.business.command.strategy;

import io.grpc.DeleteResponse;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class DeleteUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		DeleteResponse deleteResponse;

		if(Manipulator.getValue(code) != null) {
			Manipulator.removeValue(code);

			if (!Manipulator.containKey(code)) {
				deleteResponse = DeleteResponse.newBuilder()
						.setStatus(MessageMap.SUCCESS.getMessage())
						.setMessage(MessageMap.DELETE_SUCCESS.getMessage())
						.build();
			} else {
				deleteResponse = DeleteResponse.newBuilder()
						.setStatus(MessageMap.ERROR.getMessage())
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.build();
			}
		} else {
			deleteResponse = DeleteResponse.newBuilder()
					.setStatus(MessageMap.ERROR.getMessage())
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.build();
		}

		genericCommand.getOutput().onNext(deleteResponse);
		genericCommand.getOutput().onCompleted();
	}
}