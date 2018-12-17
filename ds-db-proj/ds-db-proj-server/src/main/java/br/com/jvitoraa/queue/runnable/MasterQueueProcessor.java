package br.com.jvitoraa.queue.runnable;

import java.util.Objects;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.grpc.vo.RangeVO;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MasterQueueProcessor implements Runnable {

	private QueueController queueController;
	private RangeVO range;
	private Integer serverN;

	@Override
	public void run() {
		while (true) {
			this.process();
		}
	}

	private void process() {
		CommandDto command = this.queueController.getFstQueue().poll();
		if (Objects.nonNull(command)) {
			if (this.inRange(command)) {
				this.queueController.getSndQueue().offer(command);
				this.queueController.getTrdQueue().offer(command);
			} else {
				this.queueController.getFthQueue().offer(command);
			}
		}
	}

	private boolean inRange(CommandDto command) {
		return this.range.getListOfRanges().get(serverN).getRange().contains(command.getId().intValue());
	}

}
