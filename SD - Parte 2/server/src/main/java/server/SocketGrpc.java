package server;

import model.Operator;
import threads.ThreadGRPC;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;
import org.socketUdp.grpc.MakeOperationGrpc;
import org.socketUdp.grpc.Operation;
import org.socketUdp.grpc.OperationResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class SocketGrpc extends MakeOperationGrpc.MakeOperationImplBase 
{
    public SocketGrpc()
    {
        ThreadGRPC.init();
    }

    @Override
    public void makeOperation(Operation request, StreamObserver<OperationResponse> responseObserver) 
    {
        getInstance().addArrivingGrpc(new ArrivingGrpc(request, responseObserver));
    }
}
