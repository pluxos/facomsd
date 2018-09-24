package servidor.command;

import servidor.dataBase.Data;

public class ExecuteCommand {

	public String execute(String comando) {

		int tipo = ComandQuery.getTipoComando(comando);

		switch (tipo) {
		case 1:// create
			try {
				System.out.println("executando create");
				return Data.create(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
			}
			catch (Exception e){
				System.out.println(e.toString());
			}

		case 2:// read
			try {
				System.out.println("executando read");
				return Data.read(ComandQuery.getKey(comando));
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
			

		case 3:// update
			try {
				System.out.println("executando update");
				return Data.update(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
			
 
		case 4:// delete
			try {
				System.out.println("executando delete");
				return Data.delete(ComandQuery.getKey(comando));
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}

		default:
			return "Comando invalido";
		}
	}
}
