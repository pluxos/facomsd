package br.com.jvitoraa.queue.runnable;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.queue.controller.LogController;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.Data;

@Data
public class LogQueueProcessor implements Runnable {
	
	public LogQueueProcessor(QueueController queueController) {
		this.queueController = queueController;
	}

	private QueueController queueController;
	private LogController logController;
	private static final Logger LOGGER = Logger.getLogger(LogQueueProcessor.class.getName());

	@Override
	public void run() {
		try {
			this.logController = new LogController("joao");
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}
		while (true) {
			try {
				this.process();
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
			}
		}
	}

	private void process() throws IOException {

		CommandDto command = this.queueController.getSndQueue().poll();

		if (Objects.nonNull(command)) {
			if (!command.getTypeOfCommand().equals("READ")) {
				this.logController.getWriter().write(command.generateLogString());
				this.logController.getWriter().append(StringUtils.LF);
				this.logController.getWriter().flush();
			}
		}
	}

}
