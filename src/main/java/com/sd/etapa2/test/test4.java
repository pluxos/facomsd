package com.sd.etapa2.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.sd.etapa2.server.ServerNode;

public class test4 {
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		ServerNode server;
		try {
			server = new ServerNode(6, 1236, "Arquivo");
			server.rodar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
