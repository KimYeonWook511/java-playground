package section5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class T15_TimeoutLock {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new TestThread(), "T1");
		Thread t2 = new Thread(new TestThread(), "T2");

		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}

class TestThread extends Thread{
	private static final ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		for(int i = 1; i <= 5; ++i)
			try {
				System.out.println(currentThread().getName() + ": Lock 획득 시도..." + i);
				if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
					System.out.println(currentThread().getName() + ": Lock 획득 성공!!!");
					sleep(1000); // 1000ms 이상으로 하면 더 획득 실패가 많이 뜨게 됨! -> 당연한 논리임
					lock.unlock();
				} else {
					System.out.println("\t" + currentThread().getName() + ": Lock 획득 실패");
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
	}
}
