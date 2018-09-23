package servidor.command;

import servidor.dataBase.Data;

public class ExecuteCommand {

	public String execute(String comando) {

		int tipo = ComandQuery.getTipoComando(comando);

		switch (tipo) {
		case 1:// create
			System.out.println("executando create");
			return Data.create(ComandQuery.getKey(comando), ComandQuery.getValue(comando));

		case 2:// read
			System.out.println("executando read");
			return Data.read(ComandQuery.getKey(comando));

		case 3:// update
			System.out.println("executando update");
			return Data.update(ComandQuery.getKey(comando), ComandQuery.getValue(comando));

		case 4:// delete
			System.out.println("executando delete");
			return Data.delete(ComandQuery.getKey(comando));

		default:
			return "Comando invalido";
		}
	}
}
