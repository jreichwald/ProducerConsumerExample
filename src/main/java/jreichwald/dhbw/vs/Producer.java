package jreichwald.dhbw.vs;

import java.util.Queue;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {

	/**
	 * Shared Storage. A Queue realizes the FIFO-Principle in a 
	 * One-Way-Manner.
	 */
	private Queue<Integer> sharedStorage;

	/**
	 * Max size of storage queue
	 */
	private final int MAXSIZE;

	/**
	 * Sleeptime base value
	 */
	private final int SLEEPTIME;

	/**
	 * Log instance 
	 */
	private Logger _log = LogManager.getLogger(Producer.class);
	
	/**
	 * Default Constructor
	 * 
	 * @param sharedStorage reference to the shared storage
	 * @param maxsize max size of the shared storage 
	 * @param sleeptime random double is multiplied with sleeptime to sleep an arbitrary long time
	 */
	public Producer(Queue<Integer> sharedStorage, int maxsize, int sleeptime) {
		this.MAXSIZE = maxsize;
		this.sharedStorage = sharedStorage;
		this.SLEEPTIME = sleeptime;
	}

	/**
	 * Run-Method 
	 */
	public void run() {
		Random random = new Random(); 
		
		int i = 0; // value to be produced
		
		_log.debug("Producer thread started.");
		
		// Loop endlessly ...
		while (true) {
			synchronized (sharedStorage) { //START synchronized code block 
				while (sharedStorage.size() == MAXSIZE) {
					try {
						sharedStorage.wait(); // wait and release monitor 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				sharedStorage.add(i);
				_log.debug("Produced " + i++ +", Queue size now " + this.sharedStorage.size());
				sharedStorage.notifyAll(); // notify waiting threads that monitor will be released.
			} // END synchronized code block 
			
			// Wait for an arbitrary long time to simulate some work ...
			try {
				Thread.sleep((long) Math.ceil(random.nextDouble() * this.SLEEPTIME)); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
