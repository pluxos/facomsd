package server.business.command.strategy;

import io.grpc.GetResponse;
import server.commons.domain.GenericCommand;
import server.commons.domain.GenericResponse;
import server.commons.utils.MessageMap;
import server.model.HashMap.Manipulator;

import java.math.BigInteger;
import java.util.Arrays;

public class GetUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		GetResponse getResponse;

		byte[] res = Manipulator.getValue(code);

		if(res != null){
			getResponse = GetResponse.newBuilder()
					.setStatus(MessageMap.SUCCESS.getMessage())
					.setMessage(MessageMap.GET_SUCCESS.getMessage())
					.setData(Arrays.toString(res))
					.build();
		} else {
			getResponse = GetResponse.newBuilder()
					.setStatus(MessageMap.ERROR.getMessage())
					.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
					.build();
		}

		genericCommand.getOutput().onNext(getResponse);
		genericCommand.getOutput().onCompleted();
	}
}