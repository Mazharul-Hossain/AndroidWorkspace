package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for
 * synchronization.
 *
 * <p>
 * You must implement this.
 *
 * @see	nachos.threads.Condition
 */
public class Condition2{
    /**
     * Allocate a new condition variable.
     *
     * @param	conditionLock	the lock associated with this condition
     *				variable. The current thread must hold this
     *				lock whenever it uses <tt>sleep()</tt>,
     *				<tt>wake()</tt>, or <tt>wakeAll()</tt>.
     */
    public Condition2(Lock conditionLock) {
		this.conditionLock = conditionLock;
	
    }

    /**
     * Atomically release the associated lock and go to sleep on this condition
     * variable until another thread wakes it using <tt>wake()</tt>. The
     * current thread must hold the associated lock. The thread will
     * automatically reacquire the lock before <tt>sleep()</tt> returns.
     */
    public void sleep() {
			Lib.assert(conditionLock.isHeldByCurrentThread());

			
			
			
			boolean intpstate = Machine.interrupt().disable();
			
			conditionLock.release();
			
			waitQueue.waitForAccess(KThread.currentThread());
		    KThread.sleep();
			
			
			Machine.interrupt().restore(intpstate);
			
			
			
			
			conditionLock.acquire();
			
    }

    /**
     * Wake up at most one thread sleeping on this condition variable. The
     * current thread must hold the associated lock.
     */
    public void wake() {
		Lib.assert(conditionLock.isHeldByCurrentThread());
		
		//========================================================
		boolean intpstate = Machine.interrupt().disable();
		
		KThread thread = waitQueue.nextThread();
		if (thread != null) {
			thread.ready();
		}
		
		
		Machine.interrupt().restore(intpstate);
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
    }

    /**
     * Wake up all threads sleeping on this condition variable. The current
     * thread must hold the associated lock.
     */
    public void wakeAll() {
		Lib.assert(conditionLock.isHeldByCurrentThread());
		//========================================================
		boolean intpstate = Machine.interrupt().disable();
		KThread thread = waitQueue.nextThread();
		while(thread != null)
		{
			thread.ready();
			thread = waitQueue.nextThread();
			
			
				
			
		}
		
		Machine.interrupt().restore(intpstate);
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
    }

	private ThreadQueue waitQueue = ThreadedKernel.scheduler.newThreadQueue(false);
	
    private Lock conditionLock;
}