package br.com.jvitoraa.atomix;

import java.math.BigInteger;

import br.com.jvitoraa.repository.DatabaseRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AtomixEventController {
	
	private AtomixController atomixController; 
	private DatabaseRepository database;
	
	
	public void startController() { 
		
		while (atomixController.getAtomixReplica() == null) {
			System.out.println("Starting Replica!");
		}
		
		atomixController.getAtomixReplica().getMap("commands")
		.thenCompose(m -> m.onAdd(event -> {
			System.out.println("Creating Id: " + event.entry().getKey());
			database.create((BigInteger) event.entry().getKey(), (String) event.entry().getValue());
		}));
		
		atomixController.getAtomixReplica().getMap("commands")
		.thenCompose(m -> m.onUpdate(event -> {
			System.out.println("Updating Id: " +event.entry().getKey());
			database.update((BigInteger) event.entry().getKey(), (String) event.entry().getValue());
		}));
		
		atomixController.getAtomixReplica().getMap("commands")
		.thenCompose(m -> m.onRemove(event -> {
			System.out.println("Deleting Id: " + event.entry().getKey());
			database.delete((BigInteger) event.entry().getKey());
		}));
		
		
		
	}

}
