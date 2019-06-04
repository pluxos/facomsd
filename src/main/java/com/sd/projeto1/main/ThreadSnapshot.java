package com.sd.projeto1.main;

import com.sd.projeto1.dao.SnapshotDao;

public class ThreadSnapshot extends Thread {
    
    long U;
    public ThreadSnapshot(long U){
        this.U = U;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                
                SnapshotDao.criarSnapshot();
                // 5 minutos
                try { Thread.sleep(U * 1000); } catch(InterruptedException e) { e.printStackTrace(); }
                
            } catch (Exception ex) {
                System.out.println(""+ ex);
            }
            
        }
    }
    
}