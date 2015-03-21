package jreichwald.dhbw.vs;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {

	/**
	 * Shared Storage 
	 */
	private Queue<Integer> sharedStorage;

	/**
	 * Max size of storage.
	 */
	private final int MAXSIZE;

	/**
	 * Sleeptime coefficient
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
		int i = 0; // value to be produced
		
		_log.debug("Producer thread started.");
		
		// Loop endlessly ...
		while (true) {
			synchronized (sharedStorage) { //START synchronized code block 
				if (sharedStorage.size() == MAXSIZE) {
					try {
						sharedStorage.wait(); // wait and release monitor 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sharedStorage.add(i++);
				_log.debug("Produced " + i);
				sharedStorage.notifyAll(); // notify waiting threads that monitor will be released.
			} // END synchronized code block 
			
			// Wait for an arbitrary long time
			try {
				Thread.sleep(new Double(Math.ceil(Math.random() * SLEEPTIME))
						.longValue());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
