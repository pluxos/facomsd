package com.sd.etapa2.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sd.etapa2.grpc.ServerRequestServiceImpl;
import com.sd.etapa2.server.ServerNode;

import io.grpc.Server;
import io.grpc.ServerBuilder;

//
public class Test3 {

	public static void main(String[] args) throws IOException, InterruptedException {
		ServerNode server;
		try {
			server = new ServerNode(3, 1233, "Arquivo");
			server.rodar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
