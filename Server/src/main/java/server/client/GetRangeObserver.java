package server.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.GetRangeResponse;
import io.grpc.stub.StreamObserver;
import server.commons.Chord.Chord;
import server.commons.Chord.Node;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetRangeObserver implements StreamObserver<GetRangeResponse> {

    @Override
    public void onNext(GetRangeResponse getRangeResponse) {
        try {
            Node newNode = JsonUtils.deserialize(getRangeResponse.getNode(), Node.class);
            if(newNode.getKey() != Chord.getNode().getKey()) {
                /* Recover Data */
                TypeReference<HashMap<BigInteger, byte[]>> dbRef;
                dbRef = new TypeReference<HashMap<BigInteger, byte[]>>() {};

                HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(getRangeResponse.getData(), dbRef);
                for (Map.Entry<BigInteger, byte[]> entry : map.entrySet()) {
                    Manipulator.addValue(entry.getKey(), entry.getValue());
                }

                /* Set Range */
                TypeReference<ArrayList<Integer>> arrayRef = new TypeReference<ArrayList<Integer>>() {
                };
                myNode.setRangeWithArray(JsonUtils.deserialize(getRangeResponse.getRange(), arrayRef));
                ft.updateFT(myNode);

                /* Update Tabela de rotas */
                System.err.println("ATUALIZANDO TABELA DE ROTAS");
                ft.updateFT(newNode);
            } else {
                myNode.setNewKey();
                ft.setKey(myNode.getKey());
                System.out.println("KEY: " + myNode.getKey());
                findNode(chordIp, chordPort);
            }
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}
