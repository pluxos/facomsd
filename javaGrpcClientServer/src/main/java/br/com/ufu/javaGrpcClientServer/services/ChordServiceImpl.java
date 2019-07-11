package br.com.ufu.javaGrpcClientServer.services;

import br.com.ufu.javaGrpcClientServer.InsertRequest;
import br.com.ufu.javaGrpcClientServer.InsertResponse;
import br.com.ufu.javaGrpcClientServer.JoinRequest;
import br.com.ufu.javaGrpcClientServer.JoinResponse;
import br.com.ufu.javaGrpcClientServer.chord.Node;
import io.grpc.stub.StreamObserver;

public class ChordServiceImpl {
	/*private Node node;
	
	public ChordServiceImpl(Node _node) {
		this.node = _node;
	}
	
	@Override
	public void join(
			JoinRequest _request, 
			final StreamObserver<JoinResponse> responseObserver) {
		Node successor = this.node.find_successor(_request.getId());
		JoinResponse response = JoinResponse.newBuilder().setAddress(successor.getAddress()).setPort(successor.getPort()).setId(successor.getId()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}*/
}
