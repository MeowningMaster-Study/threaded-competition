package com.meowningmaster.threadedcompetition;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerThread extends Thread {
    private final AtomicInteger value;
    private final AtomicBoolean updatePooled;
    private final Update updateFn;
    private final int delta;


    public WorkerThread(AtomicInteger value, int delta, AtomicBoolean updatePooled, Update updateFn) {
        this.value = value;
        this.delta = delta;
        this.updatePooled = updatePooled;
        this.updateFn = updateFn;
    }

    public long fibonacci(long number) {
        if (number == 0 || number == 1)
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }

    @Override
    public void run() {
        while (!interrupted()) {
            this.fibonacci(28);
            int newValue = Math.min(Math.max(this.value.intValue() + delta, 0), 100);
            this.value.set(newValue);
            System.out.println(newValue);
            if (!updatePooled.getAndSet(true)) {
                updateFn.call();
            }
        }
    }
}
