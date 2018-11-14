package br.com.jvitoraa.queue.runnable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.jvitoraa.grpc.service.LogSnapshotIndexService;
import br.com.jvitoraa.queue.controller.SnapshotController;
import br.com.jvitoraa.repository.DatabaseRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SnapshotProcessor implements Runnable {

	private LogSnapshotIndexService logSnapshotService;
	private DatabaseRepository database;
	private Integer timer;
	private String filePath;
	private static final Logger LOGGER = Logger.getLogger(SnapshotProcessor.class.getName());

	@Override
	public void run() {
		while (true) {
			try {
				this.process();
			} catch (IOException | InterruptedException e) {
				LOGGER.warning(e.getMessage());
			}
		}
	}

	private void process() throws IOException, InterruptedException {
		Thread.sleep(timer);
		LOGGER.info("Snapshoting!");
		SnapshotController snapshotController = new SnapshotController(this.filePath,
				"snap." + logSnapshotService.getSnapshotIndex());
		database.getDatabase().entrySet().stream().forEach(entry -> {
			try {
				snapshotController.getWriter().write(entry.getKey().toString() + StringUtils.SPACE + entry.getValue());
				snapshotController.getWriter().write(StringUtils.LF);
				snapshotController.getWriter().flush();
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
			}
		});

		logSnapshotService.increaseLogIndex();
		logSnapshotService.increaseSnapshotIndex();
		this.maintainLastThree(snapshotController);
	}

	private void maintainLastThree(SnapshotController snapshotController) {
		File snapshotFolder = new File(snapshotController.getFilePath());
		File[] snapshots = snapshotFolder.listFiles();

		if (ArrayUtils.isNotEmpty(snapshots) && snapshots.length > 3) {
			Arrays.sort(snapshots, Comparator.comparingLong(File::lastModified));
			snapshots[0].delete();
		}
	}
}
