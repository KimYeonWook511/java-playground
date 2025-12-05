package section5;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  ReentrantLock - 함수 바디 전체가 아니라 특정 부분에 대해서만 동기화를 시도하기 위한 방법
 *  - synchronized는 Lock Flag를 이용하는 반면, ReentrantLock은 별도 객체를 이용해 동기화 함
 *  - ReentrantLock은 보통 임계 영역에 코드가 많아져서 발생할 수 있는 성능 저하 문제를 해결하기 위해 사용함
 *    (임계 영역에 속한 코드는 무조건 최소화 하는 것이 좋음!!)
 */
public class T14_ReentrantLock {
	private static final ReentrantLock lock = new ReentrantLock();
	private static int counter01 = 0;
	private static int counter02 = 0;

	public static synchronized void increment() {
		++counter01;
	}

	public static void incCounter() {
		lock.lock();
		try { ++counter02; }
		finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 30000; i++)
					increment();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 30000; i++)
					incCounter();
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println("counter01: " + counter01);
		System.out.println("counter02: " + counter02);
	}
}
