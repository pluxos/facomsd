package br.com.jvitoraa.recovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

import br.com.jvitoraa.repository.DatabaseRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseRecoveryUtil {

	private DatabaseRepository databaseRepository;

	public void recoverFromLog() throws IOException {

		File logFile = new File("joao.txt");
		FileReader fr = new FileReader(logFile);

		BufferedReader br = new BufferedReader(fr);
		String commandString;
		while ((commandString = br.readLine()) != null) {
			String[] logEntry = commandString.split(StringUtils.SPACE);

			switch (logEntry[0]) {
			case "CREATE":
				this.databaseRepository.create(BigInteger.valueOf(Long.valueOf(logEntry[1])), logEntry[2]);
				break;
			case "UPDATE":
				this.databaseRepository.update(BigInteger.valueOf(Long.valueOf(logEntry[1])), logEntry[2]);
				break;
			case "DELETE":
				this.databaseRepository.delete(BigInteger.valueOf(Long.valueOf(logEntry[1])));
				break;
			}
		}

		br.close();

	}

}
