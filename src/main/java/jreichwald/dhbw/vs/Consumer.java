package jreichwald.dhbw.vs;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {

	/**
	 * Shared Storage 
	 */
	private Queue<Integer> sharedStorage;

	/**
	 * Max size of storage.
	 */
	private final int MAXSIZE;

	/**
	 * sleep coefficient
	 */
	private final int SLEEPTIME;

	/**
	 * Log instance 
	 */
	private Logger _log = LogManager.getLogger(Consumer.class);

	/**
	 * Default Constructor
	 * 
	 * @param sharedStorage reference to the shared storage
	 * @param maxsize max size of the shared storage 
	 * @param sleeptime random double is multiplied with sleeptime to sleep an arbitrary long time
	 */
	public Consumer(Queue<Integer> sharedStorage, int maxsize, int sleeptime) {
		this.MAXSIZE = maxsize;
		this.sharedStorage = sharedStorage;
		this.SLEEPTIME = sleeptime;
	}

	/**
	 * Run Method
	 */
	public void run() {
		_log.debug("Consumer thread started.");

		// loop endlessly 
		while (true) {
			synchronized (sharedStorage) { // START synchronized code block
				while (this.sharedStorage.size() == 0) {
					try {
						sharedStorage.wait(); //wait and release monitor 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				_log.debug("Consumed: " + sharedStorage.poll());
				sharedStorage.notifyAll(); // notify waiting threads that the monitor will be released
			} // END OF SYNCHRONIZED CODE BLOCK
			
			// Wait for an arbitrary long time to simulate processing time
			try {
				Thread.sleep(new Double(Math.ceil(Math.random() * SLEEPTIME)).longValue());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
