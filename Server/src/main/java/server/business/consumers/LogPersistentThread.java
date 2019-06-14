package server.business.consumers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import server.business.persistence.routine.Counter;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.rows.RowF2;
import server.commons.utils.JsonUtils;

public class LogPersistentThread implements Runnable {
	
	private String path;
	
	public LogPersistentThread(String path) {
		this.path = path + "log";
	}

	@Override
	public void run() {
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
				try (FileWriter writer = new FileWriter(path + (Counter.getCounter() - 1) + ".log", true);
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