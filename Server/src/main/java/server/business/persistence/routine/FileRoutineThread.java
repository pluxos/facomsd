package server.business.persistence.routine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

import server.business.persistence.Manipulator;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class FileRoutineThread extends TimerTask {

	private String basePath;

	public FileRoutineThread(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void run() {
		persistDb();
		deleteOlderLogsAndSnaps();
		Counter.writeCounterValue();
		Counter.increaseCounter();
	}

	private void persistDb() {
		try (FileWriter writer = new FileWriter(buildPath("snap", Counter.getCounter()));
				BufferedWriter bw = new BufferedWriter(writer)) {
			if (Manipulator.getDb() != null) {
				bw.write(JsonUtils.serialize(Manipulator.getDb()));
			}
		} catch (IOException | ServerException e) {
			System.err.format("IOException: %s%n", e);
		}	
	}

	private void deleteOlderLogsAndSnaps() {
		long counter = Counter.getCounter();
		for (long i = counter - 3; i > counter - 6; i--) {
			new File(buildPath("snap", i)).delete();
			new File(buildPath("log", i)).delete();
		}
	}

	private String buildPath(String type, Long counter) {
		return basePath + type + counter.toString() + ".log";
	}
}