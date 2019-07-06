package server.business.command.strategy;

import java.math.BigInteger;

import io.grpc.Context;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;
import server.business.command.RequestUtils;
import server.business.command.observer.GenericResponseObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.ChodNode;
import server.commons.domain.GenericCommand;
import server.commons.exceptions.MessageMap;
import server.commons.utils.DataCodificator;
import server.requester.CommunicationManager;

public class Create implements CommandStrategy {

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
	public void passCommand(GenericCommand genericCommand, ChodNode chodNode) {
		GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(chodNode.getIp(), chodNode.getPort());

		Context forked = Context.current().fork();
		Context old = forked.attach();

		try {
			stub.create(
					RequestUtils.getGenericRequestWithData(genericCommand),
					new GenericResponseObserver(genericCommand.getOutput())
			);
		} finally {
			forked.detach(old);
		}
	}
}