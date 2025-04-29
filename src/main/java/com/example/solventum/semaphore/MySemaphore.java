package com.example.solventum.semaphore;

import java.util.concurrent.Semaphore;

import com.example.solventum.SolventumApplication;

import jakarta.annotation.PostConstruct;

public class MySemaphore {

    private Semaphore concurrentLock;   // The semaphore that is used for locking the number of concurrent requests that can be made.

    @PostConstruct
    public void init() {
        this.concurrentLock = new Semaphore(SolventumApplication.concurrencies);
    }

    // I used the Java documentation to remember how these semaphores are used in order to implement these methods
    // https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Semaphore.html
    public boolean tryAcquire() {
        return concurrentLock.tryAcquire();
    }

    public void release() {
        concurrentLock.release();
    }
}
