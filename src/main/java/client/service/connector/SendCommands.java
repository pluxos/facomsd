package client.service.connector;

import client.commons.dto.Communication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintStream;
import java.math.BigInteger;

public class SendCommands {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static void sendCreateCommand(String command, BigInteger code, byte[] data, PrintStream output) {
		Communication c = new Communication(command, code, data);

		try {
			String t = mapper.writeValueAsString(c);
			output.println(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendGetCommand(String command, BigInteger code, PrintStream output) {
		Communication c = new Communication(command, code);

		try {
			String t = mapper.writeValueAsString(c);
			output.println(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendUpdateCommand(String command, BigInteger code, byte[] data, PrintStream output) {
		Communication c = new Communication(command, code, data);

		try {
			String t = mapper.writeValueAsString(c);
			output.println(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendDeleteCommand(String command, BigInteger code, PrintStream output) {
		Communication c = new Communication(command, code);

		try {
			String t = mapper.writeValueAsString(c);
			output.println(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
}