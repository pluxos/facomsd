package br.ufu;

import java.util.concurrent.atomic.AtomicInteger;

public class CountCheck {
    private final AtomicInteger count = new AtomicInteger();

    public void initialize(int number) {
        count.set(number);
    }

    public void addOne() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
