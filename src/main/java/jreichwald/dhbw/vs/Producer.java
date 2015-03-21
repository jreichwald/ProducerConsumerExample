package jreichwald.dhbw.vs;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Producer implements Runnable {

	private Queue<Integer> sharedStorage;

	/**
	 * Max size of storage.
	 */
	private final int MAXSIZE;

	/**
	 * Sleeptime coefficient
	 */
	private final int SLEEPTIME;

	private Logger _log = LogManager.getLogger(Producer.class);
	
	/**
	 * Default Constructor
	 * 
	 * @param sharedStorage
	 * @param maxsize
	 */
	public Producer(Queue<Integer> sharedStorage, int maxsize, int sleeptime) {
		this.MAXSIZE = maxsize;
		this.sharedStorage = sharedStorage;
		this.SLEEPTIME = sleeptime;
	}

	public void run() {
		int i = 0;
		_log.debug("Producer thread started.");
		
		while (true) {
			synchronized (sharedStorage) {
				if (sharedStorage.size() == MAXSIZE) {
					try {
						sharedStorage.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sharedStorage.add(i++);
				_log.debug("Produced " + i);
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
