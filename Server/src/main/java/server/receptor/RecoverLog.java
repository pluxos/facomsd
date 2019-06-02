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

	private String BASE_PATH;
	public RecoverLog(String path) {
		BASE_PATH = path;
	}

	@Override
	public void run() {
		Long counter = Counter.getCounter() -1;
		int i;
		for (i = 0; i < 3; i++) {
			try {
				Manipulator.clearDatabase();
				loadSnapshotToMemory(counter);
				loadLogToMemory(counter);
				break;
			} catch (Exception e) {
				counter--;
			}
		}
		if (i == 4) {
			System.err.println("Erro ao carregar o banco de dados!");
			Manipulator.clearDatabase();
		}
	}

	private void loadSnapshotToMemory(Long counter) throws ServerException, IOException {
		TypeReference<HashMap<BigInteger, byte[]>> typeRef = new TypeReference<HashMap<BigInteger, byte[]>>() {};
		HashMap<BigInteger, byte[]> map = JsonUtils.deserialize(FileUtils.read(buildFilePath("snap", counter)), typeRef);
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
		return BASE_PATH + type + counter + ".log";
	}
}