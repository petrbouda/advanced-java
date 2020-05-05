# parking - LockSupport

- it works like a binary semaphore
- we can `unpark` a thread (the thread needs to be already started) even before parking
that means that we granted him a permission to proceed automatically when he calls `park`
- permits are not cumulative (binary) - only one permit can be hold for the given thread