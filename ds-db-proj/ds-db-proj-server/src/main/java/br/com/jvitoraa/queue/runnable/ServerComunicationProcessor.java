package br.com.jvitoraa.queue.runnable;

import java.util.List;
import java.util.Objects;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.grpc.facade.ClientFacade;
import br.com.jvitoraa.grpc.service.NewConnectionService;
import br.com.jvitoraa.grpc.vo.RangeVO;
import br.com.jvitoraa.observer.GrpcObserver;
import br.com.jvitoraa.queue.controller.QueueController;
import io.grpc.ConnectivityState;

public class ServerComunicationProcessor implements Runnable {

	public ServerComunicationProcessor(Integer leftServerPort, Integer rightServerPort, RangeVO range,
			QueueController queueController, NewConnectionService newConnectionService) {
		this.leftServerPort = leftServerPort;
		this.rightServerPort = rightServerPort;
		this.range = range;
		this.queueController = queueController;
		this.newConnectionService = newConnectionService;
	}

	private Integer leftServerPort;
	private Integer rightServerPort;
	private QueueController queueController;
	private ClientFacade leftClientFacade;
	private ClientFacade rightClientFacade;
	private NewConnectionService newConnectionService;

	private RangeVO range;

	@Override
	public void run() {
		leftClientFacade = new ClientFacade(leftServerPort);
		rightClientFacade = new ClientFacade(rightServerPort);
		while (true) {
			this.process();
		}
	}

	public void process() {

		CommandDto command = this.queueController.getFthQueue().poll();

		if (Objects.nonNull(command)) {
			GrpcObserver observer = new GrpcObserver();
			observer.setPreviousObserver(command.getObserver());
			String leftOrRight = this.range.moveLeftOrRight(command.getId().intValue());

			if (leftOrRight.equals("LEFT")) {
				this.checkConnectionIfMissing(leftClientFacade, leftServerPort);
				switch (command.getTypeOfCommand()) {
				case "CREATE":
					leftClientFacade.create(command.getId(), command.getValue(), observer);
					break;
				case "READ":
					leftClientFacade.read(command.getId(), observer);
					break;
				case "UPDATE":
					leftClientFacade.update(command.getId(), command.getValue(), observer);
					break;
				case "DELETE":
					leftClientFacade.delete(command.getId(), observer);
					break;
				}
			} else if (leftOrRight.equals("RIGHT")) {
				this.checkConnectionIfMissing(rightClientFacade, rightServerPort);
				switch (command.getTypeOfCommand()) {
				case "CREATE":
					rightClientFacade.create(command.getId(), command.getValue(), observer);
					break;
				case "READ":
					rightClientFacade.read(command.getId(), observer);
					break;
				case "UPDATE":
					rightClientFacade.update(command.getId(), command.getValue(), observer);
					break;
				case "DELETE":
					rightClientFacade.delete(command.getId(), observer);
					break;
				}
			}

		}

	}

	private void checkConnectionIfMissing(ClientFacade facade, Integer port) {
		int counter = 0;
		while (!this.testConnection(facade)) {
			List<String> replicas = newConnectionService.getReplicasList(String.valueOf(port));
			facade = new ClientFacade(Integer.valueOf(replicas.get(counter)));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Changed Facade!");
		}
		
	}
	
	private boolean testConnection(ClientFacade conn) {
		return conn.getChannel().getState(true).equals(ConnectivityState.READY);
	}
 }
