package section4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  volatile은 원자성을 보장하지 않음!!!
 */
public class T10_RaceCondition {

	// static int counter = 0;
	static volatile int counter = 0;
	// static AtomicInteger counter = new AtomicInteger();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("main - begin");

		Thread[] threads = new Thread[5];
		for(int i = 0; i < 5; ++i) {
			threads[i] = new Thread(new MyThread(), "TestThread" + i);
			threads[i].start();
		}

		for(int i = 0; i < 5; ++i)
			threads[i].join();

		System.out.println("main - end, counter: " + counter);
	}

	public static class MyThread implements Runnable {
		@Override
		public void run() {
			for(int i = 0; i < 100000; ++i) {
				++counter;
				// counter.incrementAndGet();
			}
		}
	}

}
