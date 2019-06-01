package server.receptor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import server.commons.Rows.RowF2;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.receptor.routine.Counter;

public class ThreadLog implements Runnable {
	
	private static final String LOG_PATH = "src/main/resources/log";

	@Override
	public void run() {
		// Consumir de f2 e persistir em LOG
		for (;;) {
			GenericCommand genericCommand = null;
			try {
				genericCommand = RowF2.getFifo().take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Method method = null;
			if (genericCommand != null && genericCommand.getMethod() != null) {
				method = Method.getMethod(genericCommand.getMethod());
			}
			if (!Method.GET.equals(method)) {
				try (FileWriter writer = new FileWriter(LOG_PATH + (Counter.getCounter() - 1) + ".log", true);
						BufferedWriter bw = new BufferedWriter(writer)) {
					bw.append(String.valueOf(JsonUtils.serialize(genericCommand)));
					bw.newLine();
				} catch (IOException | ServerException e) {
					e.printStackTrace();
				}
			}
		}
	}
}