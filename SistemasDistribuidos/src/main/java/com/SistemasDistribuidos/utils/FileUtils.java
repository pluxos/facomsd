package com.SistemasDistribuidos.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * 
 * @author luizw
 *
 */
public class FileUtils {
	
	static Logger log = Logger.getLogger("INFO: ");
	
	
    public static final int CREATE = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int SEARCH = 4;
	
	/**
	 * 
	 * @param operation
	 * @param id
	 * @param message
	 */
	@SuppressWarnings("resource")
	public static void writeFileLog(String operation, Integer id, String message) {
		String separator = "#";
		try {
			FileWriter writer = new FileWriter("File_Log", true);
			BufferedWriter bw = new BufferedWriter(writer);
			String line = operation.concat(separator).concat(String.valueOf(id)).concat(separator).concat(message);
			bw.write(line);
			bw.newLine();

		} catch (IOException e) {
			log.info("EXCEPTION: "+ e);
		}
	}

    /**
     * RETORNA TIPO DA OPEAÇÃO
     * @param tipo
     * @return
     */
    public static String operationType(int type){
        switch(type){
            case CREATE:
                return "Inserir";
            case UPDATE:
                return "Atualizar";
            case DELETE:
                return "Excluir";
            case SEARCH:
                return "Buscar";
            default:
                return "Inválido";
        }
    }
}



