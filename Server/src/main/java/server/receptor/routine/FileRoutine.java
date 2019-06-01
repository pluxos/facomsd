package server.receptor.routine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;

public class FileRoutine extends TimerTask {

	private static final String BASE_PATH  = "src/main/resources/";

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

	private static String buildPath(String type, Long counter) {
		return BASE_PATH + type + counter.toString() + ".log";
	}
}