package server.receptor;
import server.business.command.strategy.CreateUser;
import server.business.command.strategy.DeleteUser;
import server.business.command.strategy.UpdateUser;
import server.business.command.strategy.RequestStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class RecoverLog {

    public static void recover (String readFile){
        try{
            BufferedReader file = new BufferedReader(new FileReader(readFile));
            RequestStrategy method;

            while(file.ready()){
                String line = file.readLine();
                String list[] = line.split(";");

                if (list[0].equals("CREATE")){
                    method = new CreateUser();
                    method.executeCommand(list);

                } else if (list[0].equals("DELETE")){
                    method = new DeleteUser();
                    method.executeCommand(list);

                } else if (list[0].equals("UPDATE")) {
                    method = new UpdateUser();
                    method.executeCommand(list);
                }

            }
            file.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
