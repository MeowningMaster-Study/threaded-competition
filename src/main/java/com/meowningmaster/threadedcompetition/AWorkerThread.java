package com.meowningmaster.threadedcompetition;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AWorkerThread extends Thread {
    private final AtomicInteger value;
    private final AtomicBoolean updatePooled;
    private final Update updateFn;
    private final int target;


    public AWorkerThread(AtomicInteger value, int target, AtomicBoolean updatePooled, Update updateFn) {
        this.value = value;
        this.target = target;
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
            this.fibonacci(30);
            int value = this.value.get();
            int diff = Integer.compare(target, value);
            int newValue = Math.min(Math.max(value + diff, 0), 100);
            this.value.set(newValue);
            if (!updatePooled.getAndSet(true)) {
                updateFn.call();
            }
        }
    }
}
