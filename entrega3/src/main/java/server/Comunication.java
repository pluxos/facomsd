package server;

public class Comunication
{
    boolean ended = false;
    boolean dead = false;
    
    public synchronized void run() 
    {
        while (!this.ended) 
        {
            try 
            {
                wait();
            } 
            catch (InterruptedException e) 
            {
                System.out.println(e.getMessage());
            }
        }
        notifyAll();
    }

    public synchronized void notifyEnd() 
    {
        this.ended = true;
        notifyAll();
    }

    public synchronized void kill() 
    {
        this.dead = true;
    }

    public synchronized void finalRead() 
    {
        this.ended = false;
        notifyAll();
    }
}