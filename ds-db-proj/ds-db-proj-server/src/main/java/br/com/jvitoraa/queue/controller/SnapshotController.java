package br.com.jvitoraa.queue.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.Data;

@Data
public class SnapshotController {
	
	private FileWriter writer;
	private String filePath;

	public SnapshotController(String fileName) throws IOException {
		this.filePath = "./db/recover/snap/";
        File file = new File("./db/recover/snap/" + fileName + ".txt");
        file.getParentFile().mkdirs();
        writer = new FileWriter(file, true);
	}

}
