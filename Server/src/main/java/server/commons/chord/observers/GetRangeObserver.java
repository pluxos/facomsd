package server.commons.chord.observers;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.GetRangeResponse;
import io.grpc.stub.StreamObserver;
import server.business.persistence.Manipulator;
import server.commons.chord.Chord;
import server.commons.chord.FingerTable;
import server.commons.chord.Node;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.requester.GrpcCommunication;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetRangeObserver implements StreamObserver<GetRangeResponse> {

    private String chordIp;
    private int chordPort;

    public GetRangeObserver(String chordIp, int chordPort){
        this.chordIp = chordIp;
        this.chordPort = chordPort;
    }

    @Override
    public void onNext(GetRangeResponse getRangeResponse) {
        try {
            Node newNode = JsonUtils.deserialize(getRangeResponse.getNode(), Node.class);

            if (newNode.getKey() != Chord.getNode().getKey()) {
                loadDatabase(getRangeResponse);
                setRange(getRangeResponse);

                FingerTable newFt = JsonUtils.deserialize(getRangeResponse.getFingerT(), FingerTable.class);

                Chord.getFt().updateFT(newNode);
                Chord.getFt().updateFT(newFt.getMap());
            } else {
                Chord.getNode().setNewKey();
                Chord.getFt().setKey(Chord.getNode().getKey());

                GrpcCommunication.findNode(chordIp, chordPort);
            }
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase(GetRangeResponse getRangeResponse) {
        try {
            TypeReference<HashMap<BigInteger, byte[]>> dbRef = new TypeReference<HashMap<BigInteger, byte[]>>() {};
            HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(getRangeResponse.getData(), dbRef);
            for (Map.Entry<BigInteger, byte[]> entry : map.entrySet()) {
                Manipulator.addValue(entry.getKey(), entry.getValue());
            }
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    private void setRange(GetRangeResponse getRangeResponse) {
        try {
            TypeReference<ArrayList<Integer>> arrayRef = new TypeReference<ArrayList<Integer>>() {};
            Chord.getNode().setRangeWithArray(JsonUtils.deserialize(getRangeResponse.getRange(), arrayRef));
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) { }

    @Override
    public void onCompleted() { }
}