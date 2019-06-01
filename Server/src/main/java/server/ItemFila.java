package server;

import java.math.BigInteger;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.stub.StreamObserver;

public class ItemFila {
    private StreamObserver<CreateResponse> responseC;
    private StreamObserver<ReadResponse> responseR;
    private StreamObserver<UpdateResponse> responseU;
    private StreamObserver<DeleteResponse> responseD;
    private byte[] key;
    private byte[] value;
    private String controll;

    public void itemFilaCreate(StreamObserver<CreateResponse> res, byte[] key, byte[] value) {
        this.responseC = res;
        this.controll = "CREATE";
        this.key = key;
        this.value = value;
    }

    public void itemFilaRead(StreamObserver<ReadResponse> res, byte[] key) {
        this.responseR = res;
        this.controll = "READ";
        this.key = key;
    }

    public void itemFilaUpdate(StreamObserver<UpdateResponse> res, byte[] key, byte[] value) {
        this.responseU = res;
        this.controll = "UPDATE";
        this.key = key;
        this.value = value;
    }

    public void itemFilaDelete(StreamObserver<DeleteResponse> res, byte[] key) {
        this.responseD = res;
        this.controll = "DELETE";
        this.key = key;
    }

    @Override
    public String toString() {
        String x = new String(controll);
        BigInteger y = new BigInteger(key);
        if (value != null) {
            String z = new String(value);
            return (x + " " + y + " " + z);
        } else {
            return (x + " " + y);
        }

    }

    public StreamObserver<CreateResponse> getResponseC() {
        return this.responseC;
    }
    public StreamObserver<ReadResponse> getResponseR() {
        return this.responseR;
    }

    public StreamObserver<UpdateResponse> getResponseU() {
        return this.responseU;
    }

    public StreamObserver<DeleteResponse> getResponseD() {
        return this.responseD;
    }


    public String getControll() {
        return controll;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getValue() {
        return value;
    }
}