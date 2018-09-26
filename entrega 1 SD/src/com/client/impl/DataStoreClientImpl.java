package com.client.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.client.ClientException;
import com.client.DataStoreClient;
import com.utils.StreamUtils;

public class DataStoreClientImpl implements DataStoreClient {

	private InetAddress inetAddress;
	private int port;

	public DataStoreClientImpl(InetAddress inetAddress, int port) {
		this.inetAddress = inetAddress;
		this.port = port;
	}

	public Socket connectSocket() throws ClientException {
		SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
		Socket socket = new Socket();
		try {
			socket.connect(socketAddress);
		} catch (IOException e) {
			System.out
					.println("Erro na conexão com o socket!");
			throw new ClientException(e.getMessage());
		}
		return socket;
	}

	public void disconnectSocket(Socket socket) throws ClientException {
		if (socket != null)
			try {
				StreamUtils.closeSocket(socket.getInputStream());
			} catch (IOException e) {
				System.out
						.println("Erro para fechar socket!");
				throw new ClientException(
						"Erro para fechar socket!");
			}
	}

	public void write(String name, byte[] data) throws ClientException {
		System.out
				.println("Inserindo...");
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("write", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			StreamUtils.writeLine("" + ((Integer) data.length).toString(),
					socket.getOutputStream());
			StreamUtils.writeData(data, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK"))
				System.out
						.println("Inserido com sucesso!");
			else
				throw new ClientException(responseServer);
		} catch (IOException e) {
			System.out
					.println("Erro na inserção");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
	}

	public byte[] read(String name) throws ClientException {
		System.out
				.println("Executando leitura...");
		byte[] data = null;
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("read", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK")) {
				Integer length = Integer.parseInt(StreamUtils.readLine(socket
						.getInputStream()));
				data = StreamUtils.readData(length, socket.getInputStream());
				System.out
						.println("Lido com sucesso");
			} else {
				throw new ClientException(responseServer);
			}
		} catch (IOException e) {
			System.out
					.println("Erro na leitura");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
		return data;
	}

	public void delete(String name) throws ClientException {
		System.out
				.println("Deletando...");
		Socket socket = null;
		try {
			socket = connectSocket();
			StreamUtils.writeLine("delete", socket.getOutputStream());
			StreamUtils.writeLine(name, socket.getOutputStream());
			String responseServer = StreamUtils.readLine(socket
					.getInputStream());
			if (responseServer != null && responseServer.equals("OK"))
				System.out
						.println("Deletado com sucesso");
			else
				throw new ClientException(responseServer);
		} catch (IOException e) {
			System.out
					.println("Erro ao deletar!");
			throw new ClientException(e.getMessage());
		} finally {
			disconnectSocket(socket);
		}
	}


}
