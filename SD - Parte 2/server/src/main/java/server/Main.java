package server;

public class Main 
{
	public static void main(String[] args) 
	{
		Thread threadMainServer = new ThreadServer();
        threadMainServer.start();
        Thread threadMainGrpc = new ThreadGrpc();
        threadMainGrpc.start();
	}
}
