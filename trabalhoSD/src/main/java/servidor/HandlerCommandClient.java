package servidor;

import servidor.menu.Menu;

public class HandlerCommandClient {
  private static String[] menu = Menu.operacoes.replace("\n", "").split(" ");
  private static String regex = "[a-zA-Z0-9]*";
  private static String regex_num = "[0-9]*";
  
  public static boolean checkComand(String command_complete) {
    command_complete = command_complete.toLowerCase().trim();
    String comando = command_complete.split(" ")[0];
    try {
      if (comando.matches(regex)) {
        for (String s : menu) {
          if (s.toLowerCase().equals(comando)) {
            if (command_complete.split(" ").length > 1) {
              if (comando.equals("create") || comando.equals("update") || comando.equals("1") || comando.equals("3")) {
                if (command_complete.split(":").length <= 1) {
                  return false;
                }
                if (command_complete.split(":")[0].split(" ")[1].isEmpty()) {
                  return false;
                }
                if (!command_complete.substring(command_complete.indexOf(" ") + 1, command_complete.indexOf(":") - 1).matches(regex_num)) {
                  return false;
                }
                return true;
              }
              if (comando.equals("read") || comando.equals("delete") || comando.equals("2") || comando.equals("4")) {
                String value = command_complete.substring(command_complete.indexOf(" ") + 1, command_complete.length());
                if (value.matches(regex_num))
                  return true;
              }
            }
          }
        }
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }
}
