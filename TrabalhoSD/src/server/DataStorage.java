package server;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class DataStorage {

    private HashMap< BigInteger, String > executed;
    private Queue< Operation > toLog;
    private Queue< Long > toRun;
    private Queue< Arriving > arriving;

    private static DataStorage dataStorage;

    public synchronized String getExecuted( BigInteger key ) {
        return executed.get( key );
    }

    public synchronized String addExecuted( BigInteger key, String value ) {
        if ( !executed.containsKey( key ) ) executed.put( key, value );
        else return ( "Key already exists!" );

        return ( "Inserted successfully!" );
    }

    public synchronized String replaceExecuted( BigInteger key, String value ){
        if ( executed.containsKey( key ) ) executed.replace( key, value );
        else return ( "Key not exists!" );

        return ( "Update successfully!" );
    }

    private DataStorage(){
        executed = new HashMap<>();
        toLog    = new LinkedList<>();
        toRun    =  new LinkedList<>();
        arriving = new LinkedList<>();
    }

    synchronized static DataStorage getInstance(){
        if ( dataStorage == null ) dataStorage = new DataStorage();

        return dataStorage;
    }


    public synchronized void addLog( Operation operation ){
        toLog.add( operation );
    }

    synchronized Long pollToRun() {
        return toRun.poll();
    }

    synchronized Long getFirstToRun() {
        return toRun.peek();
    }

    public synchronized void addToRun( Long pid ) {
        this.toRun.add( pid );
    }

    public synchronized Operation pollLog(){
        return toLog.poll();
    }

    public synchronized void addArriving( Arriving arriving1 ){
        arriving.add( arriving1 );
    }

    public synchronized Arriving pollArriving(){
        return arriving.poll();
    }

    public synchronized void removeExecuted( BigInteger key ) {
        executed.remove( key );
    }

    public Queue< Arriving > getArriving() {
        return arriving;
    }

    public Queue< Operation > getLog() {
        return toLog;
    }
}
