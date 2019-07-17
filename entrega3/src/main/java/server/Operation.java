package server;

public class Operation
{
    private String operation;
    private int key;
    private String value;

    public Operation (String op, int chave, String valor) 
    {
        this.operation = op;
        this.key = chave;
        this.value = valor;
    }
     
    public Operation (int chave, String valor)
    {
        this.key = chave;
        this.value = valor;        
    }
    
    public Operation (String op, int chave) 
    {
        this.operation = op;
        this.key = chave;
    }

    public synchronized String getOperation() 
    {
        return this.operation;
    }

    public synchronized int getKey() 
    {
        return this.key;
    }

    public synchronized String getValue() 
    {
        return this.value;
    }

    public synchronized void setValue(String valor) 
    {
        this.value = valor;
    }

    public synchronized void setKey(int chave) 
    {
        this.key = chave;
    }

    public void printOperation() 
    {
        System.out.println(this.operation);
    }
}