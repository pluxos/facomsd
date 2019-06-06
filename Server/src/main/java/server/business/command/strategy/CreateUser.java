package server.business.command.strategy;

import io.grpc.GenericRequest;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;
import server.client.CommunicationManager;
import server.commons.Chord.Node;
import server.commons.domain.GenericCommand;
import server.commons.utils.DataCodificator;
import server.commons.utils.MessageMap;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;

public class CreateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = DataCodificator.stringToByteArray(genericCommand.getData());

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


		if(genericCommand.getOutput() != null) {
			genericCommand.getOutput().onNext(createResponse);
			genericCommand.getOutput().onCompleted();
		}
	}

	@Override
	public void passCommand(GenericCommand genericCommand, Node node) {
		GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(node.getIp(), node.getPort());

		stub.createUser(
				GenericRequest.newBuilder()
						.setCode(genericCommand.getCode().intValue())
						.setData(genericCommand.getData())
						.build(),
				new GenericResponseObserver(genericCommand.getOutput())
		);
	}
}