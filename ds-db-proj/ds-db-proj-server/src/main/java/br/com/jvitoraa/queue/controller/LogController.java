package br.com.jvitoraa.queue.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogController {
	
	private FileWriter writer;
	private String filePath;

	public LogController(String filePath, String fileName) throws IOException {
		this.filePath = filePath;
        File file = new File(filePath + fileName + ".txt");
        file.getParentFile().mkdirs();
        writer = new FileWriter(file, true);
	}
}
