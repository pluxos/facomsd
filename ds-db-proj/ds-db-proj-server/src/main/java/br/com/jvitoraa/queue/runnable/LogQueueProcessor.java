package br.com.jvitoraa.queue.runnable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.grpc.service.LogSnapshotIndexService;
import br.com.jvitoraa.queue.controller.LogController;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.Data;

@Data
public class LogQueueProcessor implements Runnable {

	public LogQueueProcessor(QueueController queueController, LogSnapshotIndexService logSnapshotService,
			String filePath) {
		this.queueController = queueController;
		this.logSnapshotService = logSnapshotService;
		this.filePath = filePath;
	}

	private LogSnapshotIndexService logSnapshotService;
	private QueueController queueController;
	private LogController logController;
	private String filePath;
	private static final Logger LOGGER = Logger.getLogger(LogQueueProcessor.class.getName());

	@Override
	public void run() {
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

		try {
			this.logController = new LogController(this.filePath, "log." + logSnapshotService.getLogIndex());
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		}

		if (Objects.nonNull(command)) {
			if (!command.getTypeOfCommand().equals("READ")) {
				this.logController.getWriter().write(command.generateLogString());
				this.logController.getWriter().append(StringUtils.LF);
				this.logController.getWriter().flush();
			}
		}

		this.maintainLastThree(logController);
	}

	private void maintainLastThree(LogController logController) {
		File snapshotFolder = new File(logController.getFilePath());
		File[] snapshots = snapshotFolder.listFiles();

		if (ArrayUtils.isNotEmpty(snapshots) && snapshots.length > 3) {
			Arrays.sort(snapshots, Comparator.comparingLong(File::lastModified));
			snapshots[0].delete();
		}
	}

}
