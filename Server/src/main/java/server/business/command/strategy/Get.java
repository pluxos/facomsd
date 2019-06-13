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

public class Get implements CommandStrategy {

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

	@Override
	public void passCommand(GenericCommand genericCommand, Node node) {
		System.err.println(node.getPort());

		GreeterGrpc.GreeterStub stub = CommunicationManager.initCommunication(node.getIp(), node.getPort());

		Context forked = Context.current().fork();
		Context old = forked.attach();

		try {
			stub.get(
					RequestUtils.getGenericRequest(genericCommand),
					new GenericResponseObserver(genericCommand.getOutput())
			);
		} finally {
			forked.detach(old);
		}
	}
}