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

	public LogController(String fileName) throws IOException {
        File file = new File(fileName + ".txt");
        writer = new FileWriter(file, true);
	}
}
