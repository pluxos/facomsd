package com.sd.etapa2.grpc;

import java.math.BigInteger;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import com.sd.etapa2.grpc.ServerRequest.CreateRequest;
import com.sd.etapa2.grpc.ServerRequest.CreateResponse;
import com.sd.etapa2.grpc.ServerRequest.DeleteRequest;
import com.sd.etapa2.grpc.ServerRequest.DeleteResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadRequest;
import com.sd.etapa2.grpc.ServerRequest.ReadResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest;
import com.sd.etapa2.grpc.ServerRequest.SairRequest;
import com.sd.etapa2.grpc.ServerRequest.SairResponse;
import com.sd.etapa2.grpc.ServerRequest.UpdateRequest;
import com.sd.etapa2.grpc.ServerRequest.UpdateResponse;
import com.sd.etapa2.grpc.ServerRequestServiceGrpc.ServerRequestServiceImplBase;
import com.sd.etapa2.util.Banco;
import com.sd.etapa2.util.Operacao;
import com.sd.etapa2.util.Tipo;

import io.grpc.stub.StreamObserver;

//Implementação das funções para a comunicação entre o cliente e o servidor pelo gRPC
public class ServerRequestServiceImpl extends ServerRequestServiceImplBase {

	private Banco banco;
	private Vector<Integer> antecessores;
	private ArrayBlockingQueue<Operacao> filaComandos;
	//Fila para as chaves que não é de responsabilidade desse servidor
//	private ArrayBlockingQueue<Operacao> filaRedirecionamento;

	public ServerRequestServiceImpl(Banco banco, ArrayBlockingQueue<Operacao> filaComandos/*,
			ArrayBlockingQueue<Operacao> filaRedirecionamento*/) {
		this.banco = banco;
		this.filaComandos = filaComandos;
//		this.filaRedirecionamento = filaRedirecionamento;		
	}

	public boolean possuiElemento(BigInteger chave) {
		return (banco.existeElemento(chave));
	}

	@Override
	public void creat(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {

		BigInteger chave = BigInteger.valueOf(request.getChave());
		String valor = request.getValor();
		String mensagem = "";
		
		//Confere o servidor é responsável pelo elemento, caso não seja ele é colocado na fila
		//de redirecionamento, um treço de código parecido é repetido em todas as operações
//		if (!ehResponsavel(chave)) {
//
//			Operacao op = new Operacao();
//			op.setChave(chave);
//			op.setValor(valor);
//			op.setTipo(Tipo.CREATE);
//			try {
//				filaRedirecionamento.put(op);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		} else 
			if (possuiElemento(chave)) {
			mensagem = "Elemento já Existe!";
		} else {
			Operacao op = new Operacao();
			op.setChave(chave);
			op.setValor(valor);
			op.setTipo(Tipo.CREATE);
			try {
				filaComandos.put(op);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mensagem = "Elemento Inserido!";
		}

		CreateResponse response = CreateResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	private boolean ehResponsavel(BigInteger chave) {
		return antecessores.contains(chave.intValue());
	}

	@Override
	public void read(ReadRequest request, StreamObserver<ReadResponse> responseObserver) {
		BigInteger chave = BigInteger.valueOf(request.getChave());
		String valor = "";
		String mensagem = "";

//		if (!ehResponsavel(chave)) {
//
//			mensagem = "Server nao é responsável";
//			Operacao op = new Operacao();
//			op.setChave(chave);
//			op.setTipo(Tipo.READ);
//			try {
//				filaRedirecionamento.put(op);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

//		} else 
			if (banco.existeElemento(chave)) {
			valor = banco.getValor(chave);
			mensagem = "Chave: " + chave + " Valor: " + valor;
		} else {
			mensagem = "Elemento não Existe";
		}

		ReadResponse readResponse = ReadResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(readResponse);
		responseObserver.onCompleted();

	}

	@Override
	public void update(UpdateRequest request, StreamObserver<UpdateResponse> responseObserver) {
		BigInteger chave = BigInteger.valueOf(request.getChave());
		String valor = request.getValor();
		String mensagem;
//		if (!ehResponsavel(chave)) {
//
//			mensagem = "Server nao é responsável";
//			Operacao op = new Operacao();
//			op.setChave(chave);
//			op.setValor(valor);
//			op.setTipo(Tipo.UPDATE);
//			try {
//				filaRedirecionamento.put(op);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		} else

		if (banco.existeElemento(chave)) {

			Operacao op = new Operacao();
			op.setChave(chave);
			op.setValor(valor);
			op.setTipo(Tipo.UPDATE);
			try {
				filaComandos.put(op);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			mensagem = "Elemento Substituido!";
		} else {
			mensagem = "Elemento não Existe!";
		}

		UpdateResponse response = UpdateResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
		BigInteger chave = BigInteger.valueOf(request.getChave());
		String mensagem;
//
//		if (!ehResponsavel(chave)) {
//
//			mensagem = "Server nao é responsável";
//			Operacao op = new Operacao();
//			op.setChave(chave);
//			op.setTipo(Tipo.DELETE);
//			try {
//				filaRedirecionamento.put(op);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		} else

		if (banco.existeElemento(chave)) {

			Operacao op = new Operacao();
			op.setChave(chave);
			op.setTipo(Tipo.DELETE);
			try {
				filaComandos.put(op);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mensagem = "Elemento Deletado!";
		} else {
			mensagem = "Elemento não Existe!";
		}

		DeleteResponse response = DeleteResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void readvalues(ReadValuesRequest request, StreamObserver<ReadResponse> responseObserver) {
		BigInteger chave = null;
		String valor = "";

		String mensagem = "Lista de Elementos do Server\n";
		ReadResponse response = ReadResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(response);

		List<String> elementos = banco.getElementos();
		for (String elemento : elementos) {
			mensagem = elemento.toString();
			response = ReadResponse.newBuilder().setMensagem(mensagem).build();
			responseObserver.onNext(response);
		}
		responseObserver.onCompleted();
	}

	@Override
	public void sair(SairRequest request, StreamObserver<SairResponse> responseObserver) {
		String mensagem = "Desconectando do Servidor X\nVolte Sempre =)";

		SairResponse response = SairResponse.newBuilder().setMensagem(mensagem).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}


	public void setAntecessores(Vector<Integer> antecessores) {
		this.antecessores = antecessores;
	}

}
