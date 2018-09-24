package servidor.command;

import servidor.dataBase.Data;

public class ExecuteCommand {

	public String execute(String comando) throws Exception {

		int tipo = ComandQuery.getTipoComando(comando);

		switch (tipo) {
		case 1:// create
			try {
				System.out.println("executando create");
				if (comando.contains(":") && !ComandQuery.getValue(comando).equals("")) {
					return Data.create(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
				}
				return "Formato invalido";
			} catch (Exception e) {
			}

		case 2:// read
			try {
				System.out.println("executando read key " + ComandQuery.getKey(comando));
				if (!ComandQuery.getKey(comando).equals(" ")) {
					return Data.read(ComandQuery.getKey(comando));
				}
				return "Informe a key";
			} catch (Exception e) {
			}

		case 3:// update
			try {
				System.out.println("executando update");
				if (comando.contains(":")&& !ComandQuery.getValue(comando).equals("")) {
					return Data.update(ComandQuery.getKey(comando), ComandQuery.getValue(comando));
				}
				return "Formato invalido";
			} catch (Exception e) {
			}

		case 4:// delete
			try {
				System.out.println("executando delete");
				return Data.delete(ComandQuery.getKey(comando));
			} catch (Exception e) {
			}

		default:
			return "Comando invalido";
		}
	}
}
