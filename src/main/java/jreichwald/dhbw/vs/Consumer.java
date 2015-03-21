package jreichwald.dhbw.vs;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {

	private Queue<Integer> sharedStorage;

	/**
	 * Max size of storage.
	 */
	private final int MAXSIZE;

	/**
	 * sleep coefficient
	 */
	private final int SLEEPTIME;

	private Logger _log = LogManager.getLogger(Consumer.class);

	/**
	 * Default Constructor
	 * 
	 * @param sharedStorage
	 * @param maxsize
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

		while (true) {
			synchronized (sharedStorage) {
				if (this.sharedStorage.size() == 0) {
					try {
						sharedStorage.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				_log.debug("Consumed: " + sharedStorage.poll());
				sharedStorage.notifyAll();
			}
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
