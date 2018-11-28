package org.kim.grpc.server;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ThreadMain threadMain;

        for (int i = 0; i < 5; i++) {
            threadMain = new ThreadMain();
            threadMain.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
