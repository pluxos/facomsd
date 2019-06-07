package com.sd.etapa2.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import com.sd.etapa2.grpc.ServerRequestServiceImpl;
import com.sd.etapa2.server.ServerNode;

import io.grpc.Server;
import io.grpc.ServerBuilder;

//
public class IniciaServer {
	public static void main(String[] args) {

		ServerNode server;
		try {
			server = new ServerNode(0, 1230, "arquivo.txt");
			server.rodar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
