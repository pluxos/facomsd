package com.sd.projeto1.service;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextService extends ContextServiceGrpc.ContextServiceImplBase {

    private Queue<String> logQueue;
    private Queue<String> executeQueue;

    private Map<String, List<StreamObserver<SubscribeResponse>>> observers;


    public ContextService(Queue<String> logQueue, Queue<String> executeQueue, Map<String, List<StreamObserver<SubscribeResponse>>> observers) {
        super();
        this.logQueue = logQueue;
        this.executeQueue = executeQueue;
        this.observers = observers;
    }

    @Override
    public void insert(ContextRequest request, StreamObserver<ContextResponse> responseObserver) {
        messageToQueue(request.getInstruction());

        String[] instrucao = request.getInstruction().split(";");
        Mapa mapa = new Mapa();
        mapa.setChave(Integer.parseInt(instrucao[1]));
        mapa.setTexto(instrucao[2]);
        mapa.setTipoOperacaoId(1);
        try {
            MapaDao.salvar(mapa);


        } catch (Exception ex) {
            Logger.getLogger(ContextService.class.getName()).log(Level.SEVERE, null, ex);
        }


        ContextResponse response = ContextResponse.newBuilder().setMessage("Mensagem com a chave " + instrucao[1] + " foi inserido com sucesso").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(ContextRequest request, StreamObserver<ContextResponse> responseObserver) {
        messageToQueue(request.getInstruction());

        String[] instrucao = request.getInstruction().split(";");
        Mapa mapa = new Mapa();
        mapa.setChave(Integer.parseInt(instrucao[1]));
        mapa.setTipoOperacaoId(3);
        try {
            MapaDao.excluir(mapa);

        } catch (Exception ex) {
            Logger.getLogger(ContextService.class.getName()).log(Level.SEVERE, null, ex);
        }

        ContextResponse response = ContextResponse.newBuilder().setMessage("Mensagem com a chave " + instrucao[1] + " foi exclu�do com sucesso").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(ContextRequest request, StreamObserver<ContextResponse> responseObserver) {
        messageToQueue(request.getInstruction());

        String[] instrucao = request.getInstruction().split(";");
        Mapa mapa = new Mapa();
        mapa.setChave(Integer.parseInt(instrucao[1]));
        mapa.setTipoOperacaoId(2);
        mapa.setTexto(instrucao[2]);
        try {
            MapaDao.editar(mapa);
        } catch (Exception ex) {
            Logger.getLogger(ContextService.class.getName()).log(Level.SEVERE, null, ex);
        }

        ContextResponse response = ContextResponse.newBuilder().setMessage("Mensagem com a chave " + instrucao[1] + " foi atualizado com sucesso").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void find(ContextRequest request, StreamObserver<ContextResponse> responseObserver) {
        messageToQueue(request.getInstruction());

        String[] instrucao = request.getInstruction().split(";");
        Mapa mapa = new Mapa();
        mapa.setChave(Integer.parseInt(instrucao[1]));

        String stringify = MapaDao.buscar(mapa);
        ContextResponse response = ContextResponse.newBuilder().setMessage(stringify).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subscribe(SubscribeRequest request, StreamObserver<SubscribeResponse> responseObserver) {
        List<StreamObserver<SubscribeResponse>> registry = observers.get(request.getKey());
        if (registry != null) {
            registry.add(responseObserver);
            observers.put(request.getKey(), registry);
        } else {
            List<StreamObserver<SubscribeResponse>> list = new ArrayList<StreamObserver<SubscribeResponse>>();
            list.add(responseObserver);
            observers.put(request.getKey(), list);
        }
        SubscribeResponse response = SubscribeResponse.newBuilder().setMessage("Monitoramento realizado com sucesso").build();
        responseObserver.onNext(response);
    }

    private void messageToQueue(String message) {

        List<String> params = Arrays.asList(message.split(";"));

        if (params.get(0).toUpperCase().equals("PROCURAR") || params.get(0).toUpperCase().equals("EXCLUIR")) {
            executeQueue.add(params.get(0).toUpperCase() + ";" + params.get(1));
        } else {
            executeQueue.add(params.get(0).toUpperCase() + ";" + params.get(1) + ";" + params.get(2));
        }

    }

}
