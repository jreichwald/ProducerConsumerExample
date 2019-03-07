package jreichwald.dhbw.vs;

import java.util.LinkedList;
/**
 * Demo Implementation of the producer / consumer problem. 
 * @author Julian Reichwald <julian.reichwald@dhbw-mannheim.de>
 *
 */
public class Main {

	public static void main(String[] args) {
		
		// shared storage for producer and consumer 
		LinkedList<Integer> shared = new LinkedList<Integer>();
		
		// max capacity of storage
		int MAXSIZE = 10 ; 
		
		// sleeptime is used to "pause" a thread for a random time. 
		// Math.random() * SLEEPTIME 
		int SLEEPTIME = 300; 
		
		// Start Producer and Consumer
		// different sleeptimes are possible to demonstrate the
		// effects of a faster producer or faster consumer.  
		// MAXSIZE may not be different! 
		// It's also possible to experiment with multiple producers and consumers - just create 
		// multiple instances of them.
		Consumer consumer1 = new Consumer(shared, MAXSIZE, SLEEPTIME);
		Producer producer = new Producer(shared, MAXSIZE, SLEEPTIME);

		
		// Start Producer and Consumer Threads 
		Thread consumer1Thread = new Thread(consumer1);
		Thread producerThread = new Thread(producer);
		
		// Run the Threads 
		producerThread.start();
		consumer1Thread.start();
	}
}
