package client.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

import client.commons.dto.UserDTO;

public class BatmanCode {
	public static byte[] encode(UserDTO user) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os;
	    try {
	    	os = new ObjectOutputStream(out);
			os.writeObject(user);
		} catch (IOException e) {
			System.err.println("Não foi possivel converter o objeto, tente novamete ARROMBADO");
		}
	    return out.toByteArray();
	}
	
	public static UserDTO decode(byte[] byteArray) {
		ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
	    ObjectInputStream is;
		try {
			is = new ObjectInputStream(in);
			return (UserDTO) is.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Não foi possivel converter os dados do servidor, tente novamete FILHA DA PUTAAA");
		}
		return null;
	}
	
	public static BigInteger stringToBigInteger(String str) {
		return new BigInteger(str.getBytes());
	}
}
