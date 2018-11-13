package br.com.jvitoraa.queue.runnable;

import java.util.Objects;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MasterQueueProcessor implements Runnable {
	
	QueueController queueController;
	
	@Override
	public void run() {
		while (true) {
			this.process();
		}
	}
	
	private void process() {
		CommandDto command = this.queueController.getFstQueue().poll();
		if (Objects.nonNull(command)) {
			this.queueController.getSndQueue().offer(command);
			this.queueController.getTrdQueue().offer(command);
		}
		
	}

}
