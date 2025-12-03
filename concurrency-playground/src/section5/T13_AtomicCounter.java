package section5;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Non-Blocking 동기화
 *  - Lock free 구조에 spin lock이 존재함
 *  	- 경쟁 조건에서 위험을 일부 감수하고 작업을 진행(spin lock)하면 스레드 상태 전환 없이 빠르게 작업을 처리할 수 있음 (결과적으로 non-blocking 동기화)
 *  	- cpu 수준에서 원자적 연산을 이용함 (AtomicInteger)
 */
public class T13_AtomicCounter {
	static AtomicInteger counter = new AtomicInteger(0);
	//    public synchronized static void incCounter() {
	//        ++counter;
	//    }

	public static void main(String[] args) throws InterruptedException {
		System.out.println("main - begin");

		Thread[] threads = new Thread[3];
		for(int i = 0; i < 3; ++i) {
			threads[i] = new Thread(new MyThread(), "TestThread" + i);
			threads[i].start();
		}

		for(int i = 0; i < 3; ++i)
			threads[i].join();

		System.out.println("main - end, counter: " + counter);
	}

	public static class MyThread implements Runnable {
		@Override
		public void run() {
			for(int i = 0; i < 100000; ++i) {
				counter.incrementAndGet();
				//Main.incCounter();
			}
		}
	}
}
