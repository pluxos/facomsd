package br.com.jvitoraa.queue.runnable;

import java.util.Objects;

import org.apache.commons.lang3.Range;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.grpc.facade.ClientFacade;
import br.com.jvitoraa.grpc.vo.RangeVO;
import br.com.jvitoraa.observer.GrpcObserver;
import br.com.jvitoraa.queue.controller.QueueController;

public class ServerComunicationProcessor implements Runnable {

	public ServerComunicationProcessor(Integer leftServerPort, Integer rightServerPort, RangeVO range,
			QueueController queueController) {
		this.leftServerPort = leftServerPort;
		this.rightServerPort = rightServerPort;
		this.range = range;
		this.queueController = queueController;
	}

	private Integer leftServerPort;
	private Integer rightServerPort;
	private QueueController queueController;
	private ClientFacade leftClientFacade;
	private ClientFacade rightClientFacade;

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
			Range<Integer> leftRange = Range.between(range.getLeftMinVal(), range.getLeftMaxVal());
			Range<Integer> rightRange = Range.between(range.getRightMinVal(), range.getRightMaxVal());

			if (leftRange.contains(command.getId().intValue())) {
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
			} else if (rightRange.contains(command.getId().intValue())) {
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
}
