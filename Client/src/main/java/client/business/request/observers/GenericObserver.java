package client.business.request.observers;

import client.commons.utils.MessageMap;
import io.grpc.GenericResponse;
import io.grpc.stub.StreamObserver;

public class GenericObserver implements StreamObserver<GenericResponse> {

    @Override
    public void onNext(GenericResponse genericResponse) {
        if(genericResponse.getStatus().equals(MessageMap.ERROR.getMessage())) {
            System.err.println(genericResponse.getStatus().toUpperCase() + ": "+ genericResponse.getMessage()+"\n");
        } else {
            System.out.println(genericResponse.getStatus().toUpperCase() + ": "+ genericResponse.getMessage());
            System.out.println(genericResponse.getData() +"\n");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onCompleted() {

    }
}
