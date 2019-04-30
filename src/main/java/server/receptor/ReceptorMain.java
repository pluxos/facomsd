package server.receptor;

import server.commons.Rows.RowF1;
import server.commons.domain.GenericCommand;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import server.commons.utils.ResponseError;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ReceptorMain implements Runnable {
	private Socket socket;
	private Scanner input;
	private PrintStream output;

	public ReceptorMain(Socket socket) throws IOException {
		this.socket = socket;
		this.input = new Scanner(socket.getInputStream());
		this.output = new PrintStream(this.socket.getOutputStream());
	}

	public void run() {
		while (this.input.hasNextLine()) {
			String req = this.input.nextLine();

			try {
				GenericCommand gc = JsonUtils.deserialize(req, GenericCommand.class);

				gc.setOutput(this.output);

				RowF1.addItem(gc);
			} catch (ServerException e) {
				System.out.println(e.getMessage());
				ResponseError.sendError(this.output, e.getErrorMessage());
			}
		}
	}
}
