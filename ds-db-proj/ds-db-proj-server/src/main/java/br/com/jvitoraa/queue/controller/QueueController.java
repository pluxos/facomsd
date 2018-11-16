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
	
	public QueueController() {
		this.fstQueue = new ArrayBlockingQueue<CommandDto>(10);
		this.sndQueue = new ArrayBlockingQueue<CommandDto>(10);
		this.trdQueue = new ArrayBlockingQueue<CommandDto>(10);
		this.fthQueue = new ArrayBlockingQueue<CommandDto>(10);
	}
	
	public void receiveCommand(CommandDto command) {
		fstQueue.offer(command);
	}
	
}
