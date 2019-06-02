package server.receptor.routine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Counter {
	
	private static String PATH  = "counter.log";
	private static Long counter = (long) 0;
	
	static {
		counter = getCounterValue();
		if (counter != (long) 0) {
			counter++;
		}
	}

	public static void setPath(String path) {
		PATH = path + PATH;
	}
	
	public static long getCounter() {
		if (!counterFileExists()) {
			writeCounterValue();
		} 
		return counter;
	}
	
	private static boolean counterFileExists() {
		return new File(PATH).exists();
	}
	
	public static void writeCounterValue() {
		try (FileWriter writer = new FileWriter(PATH);
				BufferedWriter bw = new BufferedWriter(writer)) {
			bw.write(String.valueOf(counter));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void increaseCounter() {
		counter++;
	}
	
	@SuppressWarnings("resource")
	private static Long getCounterValue() {
		try {
			return Long.parseLong(new BufferedReader(new FileReader(PATH)).readLine());
		} catch (NumberFormatException | IOException e) {
			return (long) 0;
		}
	}
}