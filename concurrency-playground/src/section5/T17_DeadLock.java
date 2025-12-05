package section5;

import static java.lang.Thread.*;

/**
 *  Thread.wait()을 사용할 때 파라미터(ms)를 주지 않으면 무한 대기에 빠질 수 있으니 주의할 것
 */
public class T17_DeadLock {
	public static void main(String[] args) throws InterruptedException {
		MyMonitorLock2 myLock = new MyMonitorLock2();

		Thread consumer1 = new Thread(new Runnable() {
			@Override
			public void run() {
				myLock.consume1();
			}
		});

		Thread consumer2 = new Thread(new Runnable() {
			@Override
			public void run() {
				myLock.consume2();
			}
		});

		consumer1.start();
		sleep(100);
		consumer2.start();

		consumer1.join();
		consumer2.join();
		System.out.println("Main - end");
	}
}

class MyMonitorLock2 {
	public void consume1() {
		System.out.println("MyMonitorLock.consume1() - begin");
		synchronized (this) {
			try {
				System.out.println("MyMonitorLock.consume1() - before wait()");
				wait(1000);
				System.out.println("MyMonitorLock.consume1() - after wait()");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("MyMonitorLock.consume()1 - end");
	}

	public void consume2() {
		System.out.println("MyMonitorLock.consume2() - begin");
		synchronized (this) {
			try {
				System.out.println("MyMonitorLock.consume2() - before wait()");
				wait(1000);
				System.out.println("MyMonitorLock.consume2() - after wait()");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("MyMonitorLock.consume2() - end");
	}
}