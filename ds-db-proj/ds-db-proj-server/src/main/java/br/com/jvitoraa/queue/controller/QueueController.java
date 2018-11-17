package br.com.jvitoraa.queue.controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import br.com.jvitoraa.grpc.dto.CommandDto;
import lombok.Data;

@Data
public class QueueController {
	
	private BlockingQueue<CommandDto> fstQueue;
	private BlockingQueue<CommandDto> sndQueue;
	private BlockingQueue<CommandDto> trdQueue;
	private BlockingQueue<CommandDto> fthQueue;
	
	public QueueController(Integer queueSize) {
		this.fstQueue = new ArrayBlockingQueue<CommandDto>(queueSize);
		this.sndQueue = new ArrayBlockingQueue<CommandDto>(queueSize);
		this.trdQueue = new ArrayBlockingQueue<CommandDto>(queueSize);
		this.fthQueue = new ArrayBlockingQueue<CommandDto>(queueSize);
	}
	
	public void receiveCommand(CommandDto command) {
		fstQueue.offer(command);
	}
	
}
