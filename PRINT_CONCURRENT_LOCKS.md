# print-concurrent-locks

- `java -XX:+PrintConcurrentLocks PrintConcurrentLocks.java`

- print locks ` jcmd $(pgrep -f PrintConcurrentLocks) Thread.print -l -e`

```
/**
 * Information about a <em>lock</em>.  A lock can be a built-in object monitor,
 * an <em>ownable synchronizer</em>, or the {@link Condition Condition}
 * object associated with synchronizers.
 * <p>
 * <a id="OwnableSynchronizer">An ownable synchronizer</a> is
 * a synchronizer that may be exclusively owned by a thread and uses
 * {@link AbstractOwnableSynchronizer AbstractOwnableSynchronizer}
 * (or its subclass) to implement its synchronization property.
 * {@link ReentrantLock ReentrantLock} and the write-lock (but not
 * the read-lock) of {@link ReentrantReadWriteLock ReentrantReadWriteLock} are
 * two examples of ownable synchronizers provided by the platform.
 *
 * <h2><a id="MappedType">MXBean Mapping</a></h2>
 * {@code LockInfo} is mapped to a {@link CompositeData CompositeData}
 * as specified in the {@link #from from} method.
 *
 * @see java.util.concurrent.locks.AbstractOwnableSynchronizer
 * @see java.util.concurrent.locks.Condition
 *
 * @author  Mandy Chung
 * @since   1.6
 */

public class LockInfo
```

```
/**
 * A synchronizer that may be exclusively owned by a thread.  This
 * class provides a basis for creating locks and related synchronizers
 * that may entail a notion of ownership.  The
 * {@code AbstractOwnableSynchronizer} class itself does not manage or
 * use this information. However, subclasses and tools may use
 * appropriately maintained values to help control and monitor access
 * and provide diagnostics.
 *
 * @since 1.6
 * @author Doug Lea
 */
public abstract class AbstractOwnableSynchronizer
```

#### Synchronized

```
"pool-1-thread-1" #13 prio=5 os_prio=0 cpu=0,34ms elapsed=2,33s tid=0x00007f1ea4643000 nid=0x16a8 waiting on condition  [0x00007f1e70f1d000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(java.base@14/Native Method)
	at PrintConcurrentLocks.protectedArea(PrintConcurrentLocks.java:19)
	- locked <0x00000006de2c2cc8> (a java.lang.Class for PrintConcurrentLocks)
	at PrintConcurrentLocks$$Lambda$213/0x0000000800c92c40.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de2c5f00> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-2" #14 prio=5 os_prio=0 cpu=0,45ms elapsed=2,33s tid=0x00007f1ea4644800 nid=0x16a9 waiting for monitor entry  [0x00007f1e70e1c000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at PrintConcurrentLocks.protectedArea(PrintConcurrentLocks.java:19)
	- waiting to lock <0x00000006de2c2cc8> (a java.lang.Class for PrintConcurrentLocks)
	at PrintConcurrentLocks$$Lambda$213/0x0000000800c92c40.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de2c64c0> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-3" #15 prio=5 os_prio=0 cpu=0,14ms elapsed=2,33s tid=0x00007f1ea4646000 nid=0x16aa waiting for monitor entry  [0x00007f1e70d1b000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at PrintConcurrentLocks.protectedArea(PrintConcurrentLocks.java:19)
	- waiting to lock <0x00000006de2c2cc8> (a java.lang.Class for PrintConcurrentLocks)
	at PrintConcurrentLocks$$Lambda$213/0x0000000800c92c40.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de2c6740> (a java.util.concurrent.ThreadPoolExecutor$Worker)
```

#### ReentrantLock

- only for exclusive locks
- `ReentrantReadWriteLock` only for a `WRITE` lock

```
"pool-1-thread-1" #13 prio=5 os_prio=0 cpu=0,50ms elapsed=3,46s tid=0x00007f8444634800 nid=0x18eb waiting on condition  [0x00007f83ea900000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(java.base@14/Native Method)
	at PrintConcurrentLocks.lockedArea(PrintConcurrentLocks.java:32)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de1cf470> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	- <0x00000006de1d23d0> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-2" #14 prio=5 os_prio=0 cpu=0,74ms elapsed=3,46s tid=0x00007f8444636000 nid=0x18ec waiting on condition  [0x00007f83ea7ff000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de1cf470> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@14/AbstractQueuedSynchronizer.java:714)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@14/AbstractQueuedSynchronizer.java:937)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@14/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@14/ReentrantLock.java:322)
	at PrintConcurrentLocks.lockedArea(PrintConcurrentLocks.java:31)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de1d2990> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-3" #15 prio=5 os_prio=0 cpu=0,31ms elapsed=3,46s tid=0x00007f8444638000 nid=0x18ed waiting on condition  [0x00007f83ea6fe000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de1cf470> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@14/AbstractQueuedSynchronizer.java:714)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@14/AbstractQueuedSynchronizer.java:937)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@14/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@14/ReentrantLock.java:322)
	at PrintConcurrentLocks.lockedArea(PrintConcurrentLocks.java:31)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de1d2c10> (a java.util.concurrent.ThreadPoolExecutor$Worker)
```

#### LockSupport.park() of MAIN Thread

- no `Locked ownable synchronizers`

```
"main" #1 prio=5 os_prio=0 cpu=623,97ms elapsed=2,84s tid=0x00007f43d802a000 nid=0x1c5f waiting on condition  [0x00007f43e1a31000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de1f0590> (a java.lang.Object)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:51)
	at PrintConcurrentLocks.main(PrintConcurrentLocks.java:14)
	at jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(java.base@14/Native Method)
	at jdk.internal.reflect.NativeMethodAccessorImpl.invoke(java.base@14/NativeMethodAccessorImpl.java:62)
	at jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(java.base@14/DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(java.base@14/Method.java:564)
	at com.sun.tools.javac.launcher.Main.execute(jdk.compiler@14/Main.java:415)
	at com.sun.tools.javac.launcher.Main.run(jdk.compiler@14/Main.java:192)
	at com.sun.tools.javac.launcher.Main.main(jdk.compiler@14/Main.java:132)

   Locked ownable synchronizers:
	- None
```

#### LockSupport.park() / parkNanos()

```
"pool-1-thread-1" #13 prio=5 os_prio=0 cpu=0,24ms elapsed=2,93s tid=0x00007f17f065c800 nid=0x1996 waiting on condition  [0x00007f178ecc2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:341)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:43)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de173c18> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-2" #14 prio=5 os_prio=0 cpu=0,12ms elapsed=2,94s tid=0x00007f17f065e000 nid=0x1997 waiting on condition  [0x00007f178ebc1000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:341)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:43)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de1741d8> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-3" #15 prio=5 os_prio=0 cpu=0,11ms elapsed=2,93s tid=0x00007f17f065f800 nid=0x1998 waiting on condition  [0x00007f178eac0000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:341)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:43)
	at PrintConcurrentLocks$$Lambda$219/0x0000000800c91440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de174458> (a java.util.concurrent.ThreadPoolExecutor$Worker)
```

#### LockSupport.park(Object)

```
"pool-1-thread-1" #13 prio=5 os_prio=0 cpu=0,28ms elapsed=3,29s tid=0x00007fe948682000 nid=0x1b8d waiting on condition  [0x00007fe8f28be000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de14a8f0> (a java.lang.Object)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:48)
	at PrintConcurrentLocks.lambda$main$0(PrintConcurrentLocks.java:18)
	at PrintConcurrentLocks$$Lambda$221/0x0000000800cb0440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de14d988> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-2" #14 prio=5 os_prio=0 cpu=0,13ms elapsed=3,29s tid=0x00007fe948684000 nid=0x1b8e waiting on condition  [0x00007fe8f27bd000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de14a8f0> (a java.lang.Object)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:48)
	at PrintConcurrentLocks.lambda$main$0(PrintConcurrentLocks.java:18)
	at PrintConcurrentLocks$$Lambda$221/0x0000000800cb0440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de14df58> (a java.util.concurrent.ThreadPoolExecutor$Worker)

"pool-1-thread-3" #15 prio=5 os_prio=0 cpu=0,18ms elapsed=3,29s tid=0x00007fe948685800 nid=0x1b8f waiting on condition  [0x00007fe8f26bc000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@14/Native Method)
	- parking to wait for  <0x00000006de14a8f0> (a java.lang.Object)
	at java.util.concurrent.locks.LockSupport.park(java.base@14/LockSupport.java:211)
	at PrintConcurrentLocks.parking(PrintConcurrentLocks.java:48)
	at PrintConcurrentLocks.lambda$main$0(PrintConcurrentLocks.java:18)
	at PrintConcurrentLocks$$Lambda$221/0x0000000800cb0440.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@14/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@14/ThreadPoolExecutor.java:630)
	at java.lang.Thread.run(java.base@14/Thread.java:832)

   Locked ownable synchronizers:
	- <0x00000006de14e1e8> (a java.util.concurrent.ThreadPoolExecutor$Worker)
```
