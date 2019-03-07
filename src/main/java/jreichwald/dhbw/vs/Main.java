package jreichwald.dhbw.vs;

import java.util.LinkedList;
import java.util.Queue;
/**
 * Demo Implementation of the producer / consumer problem. 
 * @author Julian Reichwald <julian.reichwald@dhbw-mannheim.de>
 *
 */
public class Main {

	public static void main(String[] args) {
		
		// shared storage for producer and consumer. 
		Queue<Integer> sharedQueue = new LinkedList<Integer>();
		
		// max capacity of storage
		int MAXSIZE = 100 ; 
		
		// sleeptime is used to "pause" a thread for a random time. 
		// Math.random() * SLEEPTIME 
		int SLEEPTIME = 200; 
		
		/** Create Producer and Consumer.
		 *  Different sleeptimes are possible to demonstrate the
		 *  effects of a faster producer or faster consumer.  
		 *  It's also possible to experiment with multiple producers and consumers - just create 
		 *  multiple instances of them.
		**/ 
		Consumer consumer1 = new Consumer(sharedQueue, SLEEPTIME);
		Producer producer = new Producer(sharedQueue, MAXSIZE, SLEEPTIME);

		
		// Start Producer and Consumer Threads 
		Thread consumer1Thread = new Thread(consumer1);
		Thread producerThread = new Thread(producer);
		
		// Run the Threads 
		producerThread.start();
		consumer1Thread.start();
	
	}
}
