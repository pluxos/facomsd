package server.business.command.strategy;

import java.math.BigInteger;

import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;
import io.grpc.ManagedChannel;
import server.business.command.RequestUtils;
import server.business.persistence.Manipulator;
import server.commons.chord.Node;
import server.commons.domain.GenericCommand;
import server.commons.exceptions.MessageMap;
import server.commons.utils.DataCodificator;
import server.requester.CommunicationManager;

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
		ManagedChannel channel = CommunicationManager.initCommunication(node.getIp(), node.getPort());
		GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);

		stub.createUser(
				RequestUtils.getGenericRequestWithData(genericCommand),
				new GenericResponseObserver(genericCommand.getOutput())
		);
	}
}