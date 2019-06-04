/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.dao;

import java.io.*;

/**
 *
 * @author Gabriel
 */
public class SnapshotDao implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static MapaDao mapaDAO = new MapaDao();
    public static int snapCount = 1;
    
    public static void criarSnapshot() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(".//app.log")); 
        String linha = "";
        while(br.ready()){ 
            linha += br.readLine() + "\n"; 
        }
        
        for(int i = snapCount; i < snapCount+1; i++){
           PrintWriter writer = new PrintWriter("snap."+i+".log", "UTF-8");
            writer.println(linha);
            writer.close();
        }
            br.close();
            if( snapCount > 3 ) {
                excluirUltimoSnapshot();
            }
            snapCount++;
    }

    public static void excluirUltimoSnapshot() throws Exception {
        File file = new File(".//snap."+ (snapCount-3) +".log");
        file.delete();
    }

}