package br.com.jvitoraa.observer;

import java.util.logging.Logger;

import com.ufu.jvitoraa.interaction.Response;

import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GrpcObserver implements StreamObserver<Response> {
	
	private static final Logger LOGGER = Logger.getLogger(GrpcObserver.class.getName());
	
	private String responseText;
	
	@Override
	public void onNext(Response value) {
		this.responseText = value.getResponseText();
		LOGGER.info(value.getResponseText());
	}

	@Override
	public void onError(Throwable t) {
		LOGGER.info(t.getMessage());
	}

	@Override
	public void onCompleted() {
		LOGGER.info("Operation completed!");
	}

}