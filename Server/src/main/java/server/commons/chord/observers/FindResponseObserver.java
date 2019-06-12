package server.commons.chord.observers;

import io.grpc.FindResponse;
import io.grpc.stub.StreamObserver;
import server.commons.exceptions.ServerException;
import server.requester.GrpcCommunication;

public class FindResponseObserver  implements StreamObserver<FindResponse> {

    public FindResponseObserver(){}

    @Override
    public void onNext(FindResponse findResponse) {
        if(!findResponse.getResponse()) {
            GrpcCommunication.findNode(findResponse.getIp(), findResponse.getPort());
        } else {
            try {
                GrpcCommunication.getRange(findResponse);
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable throwable) { }

    @Override
    public void onCompleted() { }
}