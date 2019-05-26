package server.business.command.strategy;

import io.grpc.GenericResponse;
import server.commons.domain.GenericCommand;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;

public class GetUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();

		GenericResponse getResponse;

		byte[] res = Manipulator.getValue(code);

		if(res != null){
			getResponse = GenericResponse.newBuilder()
					.setStatus(MessageMap.SUCCESS.getMessage())
					.setMessage(MessageMap.GET_SUCCESS.getMessage())
					.setData(new String(res))
					.build();
		} else {
			getResponse = GenericResponse.newBuilder()
					.setStatus(MessageMap.ERROR.getMessage())
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.build();
		}

		if(genericCommand.getOutput() != null) {
			genericCommand.getOutput().onNext(getResponse);
			genericCommand.getOutput().onCompleted();
		}
	}
}