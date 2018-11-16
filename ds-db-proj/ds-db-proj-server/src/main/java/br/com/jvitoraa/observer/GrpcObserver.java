package br.com.jvitoraa.observer;

import java.util.Objects;
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

	// Criar outro observer (onNext check null) se não passa adiante.
	StreamObserver<Response> previousObserver;

	@Override
	public void onNext(Response value) {
		this.responseText = value.getResponseText();
		LOGGER.info(value.getResponseText());

		if (Objects.nonNull(previousObserver)) {
			previousObserver.onNext(value);
		}
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