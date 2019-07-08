package atomix_lab.state_machine.server;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import atomix_lab.state_machine.command.CreateCommand;
import atomix_lab.state_machine.command.DeleteCommand;
import atomix_lab.state_machine.command.ReadQuery;
import atomix_lab.state_machine.command.UpdateCommand;
import atomix_lab.state_machine.type.Data;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class Servidor extends StateMachine
{
     
    private Mapa mapa = new Mapa();

    public Boolean Create(Commit<CreateCommand> commit){
        try{
            int i;
            CreateCommand aec = commit.operation( );
            i = mapa.create(aec.key, aec.value);
            Data e = new Data(aec.key, aec.value);
            if(i==0){
                System.out.println("Adding " + e);
                return true;
            }else return false;
            
        }finally{
            commit.close();
        }
    }

    
    public Data Read(Commit<ReadQuery> commit){
    	try{
            ReadQuery geq = commit.operation();

    		System.out.println("Mapa:" + mapa);

    		Data result = new Data(geq.key,mapa.read(geq.key)); 
    		System.out.println("Read = " + result.toString());
            return result;
        }finally{
            commit.close();
        }
    }
    
    public Boolean Update(Commit<UpdateCommand> commit){
        try{
            UpdateCommand aec = commit.operation( );
            mapa.update(aec.key, aec.value);
            Data e = new Data(aec.key, aec.value);
            System.out.println("Update " + e);
            System.out.println("Novo mapa:");
            mapa.imprimeMapa();
            return mapa.existe(aec.key);
        }finally{
            commit.close();
        }
    }

    public Boolean Delete(Commit<DeleteCommand> commit){
        try{
            DeleteCommand aec = commit.operation( );
            Data e = new Data(aec.key,mapa.read(aec.key)); 
            mapa.delete(aec.key);
            System.out.println("Deleted " + e);
            
            return !mapa.existe(aec.key);
        }finally{
            commit.close();
        }
    }

    public static void main( String[] args ){
    	int myId = Integer.parseInt(args[0]);
    	List<Address> addresses = new LinkedList<>();
    	
    	for(int i = 1; i <args.length; i+=2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}
    		
        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
                                                     .withStateMachine(Servidor::new)
                                                     .withTransport( NettyTransport.builder()
                                                                     .withThreads(4)
                                                                     .build())
                                                     .withStorage( Storage.builder()
                                                                   .withDirectory(new File("logs_"+myId)) //Must be unique
                                                                   .withStorageLevel(StorageLevel.DISK)
                                                                   .build());
        CopycatServer server = builder.build();

        if(myId == 0)
        {
            server.bootstrap().join();
        }
        else
        {
            server.join(addresses).join();
        }
    }
}

class Mapa {

	private Map < BigInteger,
	byte[] > mapa;

	public Mapa() {
		this.mapa = new HashMap < >();
	}

	public boolean existe(BigInteger o1) {
		if (mapa.get(o1) == null) return false;
		else return true;
	}

	public int create(BigInteger o1, byte[] o2) {
		if (!existe(o1)) {
			mapa.put(o1, o2);
			return 0;
		} else return - 1;
	}

	public int update(BigInteger o1, byte[] o2) {
		if (existe(o1)) {
			mapa.remove(o1);
			mapa.put(o1, o2);
			return 0;
		} else return 1;
	}

	public int delete(BigInteger o1) {
		if (existe(o1)) {
			mapa.remove(o1);
			return 0;
		} else return 1;
	}

	public byte[] read(BigInteger o1) {
		return mapa.get(o1);
	}

	public Map < BigInteger,
	byte[] > getMapa() {
		return mapa;
    }
    

    public void imprimeMapa(){
        for (Map.Entry<BigInteger, byte[]> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + "/" + new String(entry.getValue()));
        }
    }

}