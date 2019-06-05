package server.receptor;

import com.fasterxml.jackson.core.type.TypeReference;
import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.FileUtils;
import server.commons.utils.JsonUtils;
import server.model.hashmap.Manipulator;
import server.receptor.routine.Counter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class RecoverLog implements Runnable {

	private String basePath;

	public RecoverLog(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void run() {
		Long counter = Counter.getCounter() == 0 ? 0 : (Counter.getCounter() - 1);
		for (int i = 0; i < 3; i++) {
			try {
				Manipulator.clearDatabase();
				loadSnapshotToMemory(counter);
			} catch (Exception e) {
				counter--;
				continue;
			}
			try {
				loadLogToMemory(counter);
			} catch (ServerException e) {
				continue;
			} catch (IOException e) {}
			break;
		}
	}

	private void loadSnapshotToMemory(Long counter) throws ServerException, IOException {
		TypeReference<HashMap<BigInteger, byte[]>> typeRef = new TypeReference<HashMap<BigInteger, byte[]>>() {
		};
		HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(FileUtils.read(buildFilePath("snap", counter)),
				typeRef);
		for (Map.Entry<BigInteger, byte[]> entry : map.entrySet()) {
			Manipulator.addValue(entry.getKey(), entry.getValue());
		}
	}

	private void loadLogToMemory(Long counter) throws IOException, ServerException {
		BufferedReader file = null;
		FileReader reader = new FileReader(buildFilePath("log", counter));
		file = new BufferedReader(reader);
		while (file.ready()) {
			String line = file.readLine();
			GenericCommand object = JsonUtils.deserialize(line, GenericCommand.class);
			Method method = Method.getMethod(object.getMethod());
			CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
			command.executeCommand(object);
		}
		file.close();
	}

	private String buildFilePath(String type, Long counter) {
		return basePath + type + counter + ".log";
	}
}