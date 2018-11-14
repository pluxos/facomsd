package br.com.jvitoraa.queue.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.Data;

@Data
public class SnapshotController {
	
	private FileWriter writer;
	private String filePath;

	public SnapshotController(String filePath, String fileName) throws IOException {
		this.filePath = filePath;
        File file = new File(filePath + fileName + ".txt");
        file.getParentFile().mkdirs();
        writer = new FileWriter(file, true);
	}

}
