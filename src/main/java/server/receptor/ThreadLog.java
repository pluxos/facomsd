package server.receptor;

import org.apache.log4j.Logger;

import server.commons.Rows.RowF2;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class ThreadLog implements Runnable {
	
	private static final Logger log = Logger.getLogger(ThreadLog.class);

	@Override
	public void run() {
		// Consumir de f2 e persistir em LOG
		for (;;) {
			try {
				GenericCommand genericCommand = RowF2.getFifo().take();
				Method method = Method.getMethod(genericCommand.getMethod());
				if(method != Method.GET) {				
					String json = JsonUtils.serialize(genericCommand);

					log.info(json);
				}	
			} catch (InterruptedException | ServerException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}