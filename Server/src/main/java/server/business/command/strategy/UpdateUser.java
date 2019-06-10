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

public class UpdateUser implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();
		byte[] data = DataCodificator.stringToByteArray(genericCommand.getData());

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

	@Override
	public void passCommand(GenericCommand genericCommand, Node node) {
		ManagedChannel channel = CommunicationManager.initCommunication(node.getIp(), node.getPort());
		GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);

		stub.updateUser(
				RequestUtils.getGenericRequestWithData(genericCommand),
				new GenericResponseObserver(genericCommand.getOutput())
		);
	}
}