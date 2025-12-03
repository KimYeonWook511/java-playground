package section5;

/**
 *  synchronized가 적용되는 것은 속한 클래스 인스턴스
 */
public class T12_CounterObject {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("main - begin");
		MyCounter cnt = new MyCounter();

		Thread[] threads = new Thread[3];
		for(int i = 0; i < 3; ++i) {
			threads[i] = new MyThread(cnt);
			threads[i].start();
		}

		for(int i = 0; i < 3; ++i)
			threads[i].join();

		System.out.println("main - end, counter: " + cnt.getCounter());
	}
}

class MyCounter {
	private int counter = 0;
	public int getCounter() {
		return counter;
	}

	public void incCounter() {
		++counter;
	}
	public void synchIncCounter() {
		synchronized (this) {
			++counter;
		}
	}
}

class MyThread extends Thread {
	public final MyCounter counter;
	MyThread(MyCounter cnt) {
		counter = cnt;
	}

	synchronized void incInMyThread() {
		counter.incCounter();
	} // MyCounter가 아닌 나 자신(this)의 Lock Flag를 확인하는 것

	static synchronized void incStaticInMyThread(MyThread thread) {
		thread.counter.incCounter();
	}

	@Override
	public void run() {
		for(int i = 0; i < 100000; ++i) {
			// counter.incCounter();
			// incInMyThread(); // 나 자신(this)의 Lock Flag를 보고 락을 선점하는 것
			// incStaticInMyThread(this); // static 메소드는 인스턴스가 아닌 클래스의 Lock Flag를 확인함
			counter.synchIncCounter(); // MyCounter의 Lock Flag를 확인
		}
	}
}
