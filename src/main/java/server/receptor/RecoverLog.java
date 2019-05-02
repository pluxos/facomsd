package server.receptor;
import server.business.command.RequestUtils;
import server.business.command.strategy.CommandStrategy;
import server.commons.domain.GenericCommand;
import server.commons.domain.Method;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class RecoverLog implements Runnable {

    @Override
    public void run (){
        try{
            BufferedReader file = new BufferedReader(new FileReader("comand.log"));

            while(file.ready()){
                String line = file.readLine();
                try {

                    GenericCommand object = JsonUtils.deserialize(line, GenericCommand.class);
                    Method method = Method.getMethod(object.getMethod());
                    CommandStrategy command = RequestUtils.getRequestStrategyByMethod(method);
                    command.executeCommand(object);

                } catch (ServerException e) {
                    System.out.println(e.getMessage());
                }
            }
            file.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
