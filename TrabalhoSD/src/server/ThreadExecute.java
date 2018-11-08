package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static server.DataStorage.getInstance;

public class ThreadExecute extends Thread {

    private Integer port;
    private DatagramSocket serverSocket;
    private Operation operation;

    ThreadExecute( DatagramSocket serverSocket, Integer port, Operation operation ) {
        this.port = port;
        this.serverSocket = serverSocket;
        this.operation = operation;
    }

    @Override
    public void run(){

        while ( true ){
            if ( DataStorage.getInstance().getFirstToRun().equals( this.getId() ) ) {

                executeOperation();
                DataStorage.getInstance().pollToRun();
                break;
            }
        }
    }

    private void executeOperation(){

        DatagramPacket sendPacket;
        byte[] response;

        try {
            switch ( operation.getOperation() ) {
                case 0:
                    response = getInstance().addExecuted( operation.getKey(), operation.getValue() ).getBytes();
                    sendPacket = new DatagramPacket( response, response.length, InetAddress.getByName( "localhost" ), port );
                    getInstance().addLog( operation );

                    break;

                case 1:
                    response = getInstance().getExecuted( operation.getKey() ).getBytes();
                    sendPacket = new DatagramPacket( response, response.length, InetAddress.getByName( "localhost" ), port );
                    getInstance().addLog( operation );
                    break;

                case 2:
                    response = getInstance().replaceExecuted( operation.getKey(), operation.getValue() ).getBytes();
                    sendPacket = new DatagramPacket( response, response.length, InetAddress.getByName( "localhost" ), port );
                    getInstance().addLog( operation );

                    break;

                case 3:
                    getInstance().removeExecuted( operation.getKey() );
                    response = "Deletado com sucesso!".getBytes();
                    sendPacket = new DatagramPacket( response, response.length, InetAddress.getByName( "localhost" ), port );
                    getInstance().addLog( operation );

                    break;

                default:
                    response = "Operação inexistente!".getBytes();
                    sendPacket = new DatagramPacket( response, response.length, InetAddress.getByName( "localhost" ), port );
            }
            serverSocket.send( sendPacket );
        } catch ( IOException e ) { e.printStackTrace(); }
    }

    static void executeOperation( Operation operation ){

        switch ( operation.getOperation() ){

            case 0://Create
                getInstance().addExecuted( operation.getKey(),operation.getValue() );

                break;

            case 1://Read
                getInstance().getExecuted( operation.getKey() );

                break;

            case 2://Update
                getInstance().addExecuted( operation.getKey(),operation.getValue() );

                break;

            case 3://Delete
                getInstance().removeExecuted( operation.getKey() );

                break;

            default:
                break;
        }
    }
}
