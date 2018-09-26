package cliente;

public class CommandHandler {
	private String[] menu;
	private String regex = "[a-zA-Z0-9]*";

	public CommandHandler(String menu) {
		this.menu = menu.replace("\n","").split(" ");
	}

	public boolean checkComand(String command) {
		String comando = (command.split(" ")[0]).toLowerCase();

		if (comando.matches(regex)) {
			for (String s : menu) {
				if (s.toLowerCase().equals(comando)) {
					return true;
				}
			}

		}
		return false;
	}

}
