package section5;

/**
 *  synchronized 키워드는 작업 메모리와 메인 메모리 간에 동기화가 일어나게 된다.
 */
public class T11_SynchronizedCounter {

	private static int counter = 0;
	public synchronized static void incCounter() {
		++counter;
	}

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
				//++counter;
				T11_SynchronizedCounter.incCounter();
			}
		}
	}

}
