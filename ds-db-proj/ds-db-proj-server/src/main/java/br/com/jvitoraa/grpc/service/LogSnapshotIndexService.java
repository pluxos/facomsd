package br.com.jvitoraa.grpc.service;

import lombok.Data;

@Data
public class LogSnapshotIndexService {
	
	private Integer logIndex = 0;
	private Integer snapshotIndex = 1;
	
	public void increaseLogIndex() {
		this.logIndex++;
	}
	
	public void increaseSnapshotIndex() {
		this.snapshotIndex++;
	}
}
