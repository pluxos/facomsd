package br.ufu.ds.client;

import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MenuListenerTest {

    private long expKey = 0;
    private String expValue = null;

    private MenuListener menu;

    private PrintStream outputStream;

    @Before
    public void setup() throws Exception {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        this.outputStream = new PrintStream(out);

        menu = new MenuListener(in) {
            @Override
            protected void onCreateSelected(BigInteger key, ByteString value) {
                expKey = key.longValue();
                expValue = value.toStringUtf8();
            }

            @Override
            protected void onReadSelected(BigInteger key) {
                expKey = key.longValue();
            }

            @Override
            protected void onUpdateSelected(BigInteger key, ByteString value) {
                expKey = key.longValue();
                expValue = value.toStringUtf8();
            }

            @Override
            protected void onDeleteSelected(BigInteger key) {
                expKey = key.longValue();
            }

            @Override
            protected void onExit() {

            }
        };
    }

    @Test
    public void onCreate() throws Exception {
        Thread t1 = new Thread(menu);

        t1.start();

        // create with params
        outputStream.println("create 2 Hello");

        outputStream.println("exit");

        t1.join(500);

        if (t1.getState() != Thread.State.TERMINATED) {
            fail("Exit was sent for listener, but the listener not finished!");
        }

        assertEquals(2, expKey);
        assertEquals("Hello", expValue);
    }

    @Test
    public void onUpdate() throws Exception {
        Thread t1 = new Thread(menu);

        t1.start();

        // update with params
        outputStream.println("update 2 Hello");
        outputStream.println("exit");

        t1.join(500);

        if (t1.getState() != Thread.State.TERMINATED) {
            fail("Exit was sent for listener, but the listener not finished!");
        }

        assertEquals(2, expKey);
        assertEquals("Hello", expValue);
    }

    @Test
    public void onRead() throws Exception {
        Thread t1 = new Thread(menu);

        t1.start();

        // read with params
        outputStream.println("read 2");


        outputStream.println("exit");

        t1.join(500);

        if (t1.getState() != Thread.State.TERMINATED) {
            fail("Exit was sent for listener, but the listener not finished!");
        }

        assertEquals(2, expKey);
    }

    @Test
    public void onDelete() throws Exception {
        Thread t1 = new Thread(menu);

        t1.start();

        // update with params
        outputStream.println("delete 2");

        outputStream.println("exit");

        t1.join(500);

        if (t1.getState() != Thread.State.TERMINATED) {
            fail("Exit was sent for listener, but the listener not finished!");
        }

        assertEquals(2, expKey);
    }


    @Test
    public void run() throws Exception {
        Thread t1 = new Thread(menu);

        t1.start();

        outputStream.println("exit");

        t1.join(500);

        if (t1.getState() != Thread.State.TERMINATED) {
            fail("Exit was sent for listener, but the listener not finished!");
        }
    }
}