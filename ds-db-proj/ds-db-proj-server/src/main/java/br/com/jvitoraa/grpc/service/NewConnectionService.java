package br.com.jvitoraa.grpc.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NewConnectionService {
	
	@Getter
	private List<List<String>> serverGroups;
	
	public List<String> getReplicasList(String server) {
		
		Optional<List<String>> listReplicas = this.getServerGroups().stream()
				.filter(p -> p.stream().anyMatch((p1 -> p1.equals(server)))).findFirst();
		
		return listReplicas.get();
	}

}
