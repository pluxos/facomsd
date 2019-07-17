package server;

import java.util.ArrayList;

public class Queue
{
    private ArrayList<Operation> operacao = new ArrayList<Operation>();

    public void printQueue() {
        for (int i = 0; i < this.operacao.size(); i++) 
        {
            this.operacao.get(i).printOperation();
        }
    }
    
}