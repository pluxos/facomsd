package br.com.jvitoraa.grpc.dto;

import org.apache.commons.lang3.StringUtils;

import com.ufu.jvitoraa.interaction.Response;

import br.com.jvitoraa.observer.GrpcObserver;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandDto {

	public CommandDto(String typeOfCommand, Long id, StreamObserver<Response> observer) {
		this.typeOfCommand = typeOfCommand;
		this.id = id; 
		this.observer = observer;
	}
	
	private String typeOfCommand;

	private Long id;
	
	private String value;
	
	private StreamObserver<Response> observer;
	
	public String generateLogString() {

		StringBuilder sb = new StringBuilder();
		sb.append(this.typeOfCommand);
		sb.append(StringUtils.SPACE);
		sb.append(this.id);
		
		if (!typeOfCommand.equals("DELETE")) {
			sb.append(StringUtils.SPACE);
			sb.append(this.value);
		}
		
		return sb.toString();
	}
	
	public String stringify(String clusterPosition) {

		StringBuilder sb = new StringBuilder();
		sb.append(this.typeOfCommand);
		sb.append(StringUtils.SPACE);
		sb.append(this.id);
		
		if (!typeOfCommand.equals("DELETE")) {
			sb.append(StringUtils.SPACE);
			sb.append(this.value);
		}
		
		sb.append(clusterPosition);
		
		return sb.toString();
	}
	
	public CommandDto(String logString) {
		String[] toBeSerialized = logString.split(StringUtils.SPACE);
		
		this.typeOfCommand = toBeSerialized[0];
		this.id = Long.valueOf(toBeSerialized[1]);
		if (toBeSerialized.length > 2) {
			this.value = toBeSerialized[2];
		}
		
		this.observer = new GrpcObserver();
	}
	
}
