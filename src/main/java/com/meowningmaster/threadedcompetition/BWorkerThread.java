package com.meowningmaster.threadedcompetition;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BWorkerThread extends Thread {
    private final AtomicInteger value;
    private final AtomicBoolean updatePooled;
    private final Update updateFn;
    private final int target;
    private final AtomicBoolean semaphore;


    public BWorkerThread(AtomicInteger value, int target, AtomicBoolean updatePooled, Update updateFn, AtomicBoolean semaphore) {
        this.value = value;
        this.target = target;
        this.updatePooled = updatePooled;
        this.updateFn = updateFn;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        //noinspection StatementWithEmptyBody
        while (semaphore.get());
        semaphore.set(true);

        while (!interrupted()) {
            int value = this.value.get();
            int diff = Integer.compare(target, value);
            int newValue = Math.min(Math.max(value + diff, 0), 100);
            this.value.set(newValue);
            if (!updatePooled.getAndSet(true)) {
                updateFn.call();
            }
        }

        semaphore.set(false);
    }
}
