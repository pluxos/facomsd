package br.com.jvitoraa.atomix;

import br.com.jvitoraa.grpc.dto.CommandDto;
import br.com.jvitoraa.queue.controller.QueueController;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AtomixEventController {
	
	private AtomixController atomixController; 
	private QueueController queueController;
	
	
	public void startController() { 
		
		while (atomixController.getAtomixReplica() == null) {
			System.out.println("Starting Replica!");
		}
		
		atomixController.getAtomixReplica().getMap("commands")
		.thenCompose(m -> m.onAdd(event -> {
			
			System.out.println(event.entry().getValue());
			
			CommandDto command = new CommandDto((String) event.entry().getValue());
			queueController.getSndQueue().offer(command);
			queueController.getTrdQueue().offer(command);
			
		}));
		
		atomixController.getAtomixReplica().getMap("commands")
		.thenCompose(m -> m.onUpdate(event -> {
			
			System.out.println(event.entry().getValue());
			
			CommandDto command = new CommandDto((String) event.entry().getValue());
			queueController.getSndQueue().offer(command);
			queueController.getTrdQueue().offer(command);
			
			if (command.getTypeOfCommand().equals("DELETE")) {
				m.remove(command.getId());
			}
			
		}));
		
		
		
	}

}
