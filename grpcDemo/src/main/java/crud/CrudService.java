package crud;

import java.math.BigInteger;
import java.util.*;

import com.example.grpc.CRUDGrpc.CRUDImplBase;
import com.example.grpc.Comando;
import com.example.grpc.Result;

import io.grpc.stub.StreamObserver;

public class CrudService extends CRUDImplBase{
	
	Map<BigInteger, byte[]> mapa = new Hashtable<BigInteger, byte[]>();
	
	int cmd;
	BigInteger chave;
	String valor;
	
	byte[] valorByte;

	@Override
	public void create(Comando request, StreamObserver<Result> responseObserver) {
		
		chave = BigInteger.valueOf(request.getChave());
		valor = request.getValor();
		
		Result.Builder res = Result.newBuilder();
		
		if(!mapa.containsKey(chave)){
            valorByte = valor.getBytes();
            mapa.put(chave, valorByte);
            
            res.setMessage("Inserção realizada com sucesso");
            
        }else{
        	
            res.setMessage("Erro: chave já existente");
        }
		
		responseObserver.onNext(res.build());
		responseObserver.onCompleted();
	}

	@Override
	public void read(Comando request, StreamObserver<Result> responseObserver) {
		
		chave = BigInteger.valueOf(request.getChave());
		valor = request.getValor();
		
		Result.Builder res = Result.newBuilder();
		
		if(mapa.containsKey(chave)){
            valorByte = mapa.get(chave);
            String valorString = new String(valorByte);
            
            res.setMessage("valor: " + valorString);
            
        }else{
        	
            res.setMessage("Erro: a chave não existe");
        }
		
		responseObserver.onNext(res.build());
		responseObserver.onCompleted();
	}

	@Override
	public void update(Comando request, StreamObserver<Result> responseObserver) {
		
		chave = BigInteger.valueOf(request.getChave());
		valor = request.getValor();
		
		Result.Builder res = Result.newBuilder();
		
		if(mapa.containsKey(chave)){
            valorByte = valor.getBytes();
            mapa.replace(chave, valorByte);
            
            res.setMessage("Valor alterado com sucesso");
            
        }else{
        	
            res.setMessage("Erro: a chave não existe");
        }
		
		responseObserver.onNext(res.build());
		responseObserver.onCompleted();
	}

	@Override
	public void delete(Comando request, StreamObserver<Result> responseObserver) {
		
		chave = BigInteger.valueOf(request.getChave());
		valor = request.getValor();
		
		Result.Builder res = Result.newBuilder();
		
		if(mapa.containsKey(chave)){
            mapa.remove(chave);
            
            res.setMessage("Remoção realizada com sucesso");
            
        }else{
        	
            res.setMessage("Chave não existente");
        }
		
		responseObserver.onNext(res.build());
		responseObserver.onCompleted();
	}
}