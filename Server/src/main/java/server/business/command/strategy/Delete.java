package server.business.command.strategy;

import io.grpc.Context;
import io.grpc.GenericResponse;
import io.grpc.GreeterGrpc;
import server.business.command.RequestUtils;
import server.business.command.observer.GenericResponseObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.Node;
import server.commons.domain.GenericCommand;
import server.commons.exceptions.MessageMap;
import server.requester.CommunicationManager;

import java.math.BigInteger;

public class Delete implements CommandStrategy {

	@Override
	public void executeCommand(GenericCommand genericCommand) {
		BigInteger code = genericCommand.getCode();

		GenericResponse deleteResponse;

		if(Manipulator.getValue(code) != null) {
			Manipulator.removeValue(code);

			if (!Manipulator.containKey(code)) {
				deleteResponse = GenericResponse.newBuilder()
						.setStatus(MessageMap.SUCCESS.getMessage())
						.setMessage(MessageMap.DELETE_SUCCESS.getMessage())
						.build();
			} else {
				deleteResponse = GenericResponse.newBuilder()
						.setStatus(MessageMap.ERROR.getMessage())
						.setMessage(MessageMap.EXECUTION_ERROR.getMessage())
						.build();
			}
		} else {
			deleteResponse = GenericResponse.newBuilder()
					.setStatus(MessageMap.ERROR.getMessage())
					.setMessage(MessageMap.USER_NOT_FOUND.getMessage())
					.build();
		}

		if(genericCommand.getOutput() != null) {
			genericCommand.getOutput().onNext(deleteResponse);
			genericCommand.getOutput().onCompleted();
		}
	}

	@Override
	public void passCommand(GenericCommand genericCommand, Node node) {
		GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(node.getIp(), node.getPort());

		Context forked = Context.current().fork();
		Context old = forked.attach();

		try {
			stub.delete(
					RequestUtils.getGenericRequest(genericCommand),
					new GenericResponseObserver(genericCommand.getOutput())
			);
		} finally {
			forked.detach(old);
		}
	}
}