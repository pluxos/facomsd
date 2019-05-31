package model;

import java.math.BigInteger;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.stub.StreamObserver;

public class ItemFila {
    private StreamObserver<GeneratedMessageV3> response;
    private byte[] controll; // crud controll
	private byte[] key;
	private byte[] value;

	public ItemFila(StreamObserver<GeneratedMessageV3> res, byte[] controll, byte[] key, byte[] value) {
		this.response = res;
		this.controll = controll;
		this.key = key;
		this.value = value;
	}

	public ItemFila(StreamObserver<GeneratedMessageV3> res, byte[] controll, byte[] key) {
		this.response = res;
		this.controll = controll;
		this.key = key;
	}

	public void print() {
		String x = new String(controll);
		BigInteger y = new BigInteger(key);
		if (value != null) {
			String z = new String(value);
			System.out.println(x + " " + y + " " + z);
		} else {
			System.out.println(x + " " + y);
		}
	}

	@Override
	public String toString() {
		String x = new String(controll);
		BigInteger y = new BigInteger(key);
		if (value != null) {
			String z = new String(value);
			return (x + " " + y + " " + z);
		} else {
			return (x + " " + y);
		}

	}

	public StreamObserver<GeneratedMessageV3> getResponse() {
		return this.response;
	}

	public byte[] getControll() {
		return controll;
	}

	public byte[] getKey() {
		return key;
	}

	public byte[] getValue() {
		return value;
	}
}