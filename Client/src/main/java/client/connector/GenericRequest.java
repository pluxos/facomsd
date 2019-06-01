package client.connector;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;

import client.commons.domain.Method;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRequest {
	
	private Method method;
	private BigInteger code;
	private byte[] data;
	
	public GenericRequest() {}

	public GenericRequest(Method command, BigInteger code, byte[] data) {
		this.code = code;
		this.method = command;
		this.data = data;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method command) {
		this.method = command;
	}

	public BigInteger getCode() {
		return code;
	}

	public void setCode(BigInteger code) {
		this.code = code;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}