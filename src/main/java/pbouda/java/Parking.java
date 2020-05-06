package pbouda.java;

import java.util.concurrent.locks.LockSupport;

public class Parking {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            // Wait for unparking
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LockSupport.park();
            System.out.println("Hello I have been already unparked ... wheey");
        });

        // parkingBefore(thread);
        parkingAfter(thread);

        Thread.currentThread().join();
    }

    private static void parkingBefore(Thread thread) throws InterruptedException {
        thread.start();
        // a bigger pause than SLEEP inside the thread to ensure PARKING first
        Thread.sleep(3000);
        LockSupport.unpark(thread);
    }

    private static void parkingAfter(Thread thread) throws InterruptedException {
        thread.start();
        // a smaller pause than SLEEP inside the thread to ensure UNPARKING first
        Thread.sleep(1000);

        /*
         * Makes available the permit for the given thread, if it
         * was not already available. If the thread was blocked on
         * {@code park} then it will unblock.  Otherwise, its next call
         * to {@code park} is guaranteed not to block. This operation
         * is not guaranteed to have any effect at all if the given
         * thread has not been started.
         */
        LockSupport.unpark(thread);
    }
}
