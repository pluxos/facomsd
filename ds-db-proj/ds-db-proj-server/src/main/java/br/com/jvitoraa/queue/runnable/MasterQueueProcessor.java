package br.com.jvitoraa.queue.runnable;

import java.util.Objects;

import org.apache.commons.lang3.Range;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.grpc.vo.RangeVO;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MasterQueueProcessor implements Runnable {

	QueueController queueController;
	RangeVO range;

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
		return Range.between(range.getMinVal(), range.getMaxVal()).contains(command.getId().intValue());
	}

}
