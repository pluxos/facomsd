package threads;

import configuration.Properties;
import io.grpc.Server;

import java.io.IOException;
import java.util.logging.Logger;

public class ThreadGRPC extends Thread
{
	private Server server;
    private static String PORT;
    private static String IPADDRESS;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void starter(){
        try {
            PORT = Properties.getInstance().loadProperties().getProperty("servergrpc.port");
            IPADDRESS = Properties.getInstance().loadProperties().getProperty("server.address");
            server = NettyServerBuilder
                    .forPort(Integer.parseInt(PORT))
                    .addService(new SocketGrpc())
                    .build()
                    .start();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    ThreadGRPC.this.stoper();
                    System.err.println("*** server shut down");
                }
            });
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void stoper() {
        if (server != null) {
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @Override
    public void run(){
        final ThreadGRPC server = new ThreadGRPC();
        server.starter();
        logger.info("Porta do server = "+ PORT);
        try {
            server.blockUntilShutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
