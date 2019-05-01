package br.ufu.sd;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void CreateClients() {
		for (int i = 0; i < 50; i++) {
			final Client cliente = new Client();
			String input = "insert Cliente" + i;
			final InputStream in = new ByteArrayInputStream(input.getBytes());

			Thread tr = new Thread(new Runnable() {
				@Override
				public void run() {
					cliente.Initialize(in);
				}
			});
			tr.start();
			try {
				tr.join(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertTrue(true);
	}
}
