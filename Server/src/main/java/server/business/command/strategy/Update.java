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

public class Update implements CommandStrategy {

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
	public void passCommand(GenericCommand genericCommand, ChodNode chodNode) {
		GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(chodNode.getIp(), chodNode.getPort());

		Context forked = Context.current().fork();
		Context old = forked.attach();

		try {
			stub.update(
					RequestUtils.getGenericRequestWithData(genericCommand),
					new GenericResponseObserver(genericCommand.getOutput())
			);
		} finally {
			forked.detach(old);
		}
	}
}