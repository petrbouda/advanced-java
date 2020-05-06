package pbouda.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintConcurrentLocks {

    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        // park a main thread
        // Suppose no 'Locked ownable synchronizers'
        PrintConcurrentLocks.parking(lock);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            // executor.execute(PrintConcurrentLocks::synchronizedArea);
            // executor.execute(PrintConcurrentLocks::lockedArea);
            // executor.execute(PrintConcurrentLocks::parking);
            executor.execute(() -> PrintConcurrentLocks.parking(lock));
        }

        Thread.currentThread().join();
    }

    private static synchronized void synchronizedArea() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void lockedArea() {
        try {
            LOCK.lock();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void parking() {
        LockSupport.park();
    }

    private static void parking(Object lock) {
        LockSupport.park(lock);
    }
}
