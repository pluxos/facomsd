package br.com.jvitoraa.recovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.jvitoraa.grpc.service.LogSnapshotIndexService;
import br.com.jvitoraa.repository.DatabaseRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseRecoveryUtil {

	private DatabaseRepository databaseRepository;
	private LogSnapshotIndexService logSnapshotIndexService;
	private static final Logger LOGGER = Logger.getLogger(DatabaseRecoveryUtil.class.getName());

	public void executeRecovery() throws IOException {
		this.recoverFromSnap();
		this.recoverFromLog();
	}

	private void recoverFromSnap() throws IOException {

		File snapFilesPath = new File("./db/recover/snap/");

		if (!snapFilesPath.exists()) {
			snapFilesPath.mkdirs();
		}

		try {

			File[] files = snapFilesPath.listFiles();
			if (ArrayUtils.isNotEmpty(files)) {
				Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

				FileReader fr = new FileReader(files[0]);
				String indexRecovered = StringUtils.substringBetween(files[0].getAbsolutePath(), "snap.", ".txt");
				this.logSnapshotIndexService.setSnapshotIndex(Integer.valueOf(indexRecovered));
				BufferedReader br = new BufferedReader(fr);
				String commandString;
				while ((commandString = br.readLine()) != null) {
					String[] logEntry = commandString.split(StringUtils.SPACE);
					databaseRepository.create(BigInteger.valueOf(Long.valueOf(logEntry[0])), logEntry[1]);
				}

				br.close();
			}
		} catch (FileNotFoundException e) {
			LOGGER.warning(e.getMessage());
		}

	}

	private void recoverFromLog() throws IOException {

		File logFilesPath = new File("./db/recover/log/");

		if (!logFilesPath.exists()) {
			logFilesPath.mkdirs();
		}

		try {

			File[] files = logFilesPath.listFiles();
			if (ArrayUtils.isNotEmpty(files)) {

				Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

				FileReader fr = new FileReader(files[0]);
				String indexRecovered = StringUtils.substringBetween(files[0].getAbsolutePath(), "log.", ".txt");
				this.logSnapshotIndexService.setLogIndex(Integer.valueOf(indexRecovered));
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
		} catch (FileNotFoundException e) {
			LOGGER.warning(e.getMessage());
		}

	}

}
