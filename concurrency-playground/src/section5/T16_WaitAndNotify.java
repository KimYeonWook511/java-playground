package section5;

import static java.lang.Thread.*;

/**
 *  대기와 알림
 *  - 멀티스레드 환경에서 신호를 주고 받는 방식으로 흐름을 동기화 하는 구조
 *  	- 대기 후 알림
 *  	- synchronized 기반 Thread.wait(), Thread.notify()
 *  	- LockSupport.park(), LockSupport.unpark()
 *  - 개별 스레드의 실행 흐름은 스케줄링에 따라 달라질 수 있으므로 대기와 알림은 반드시 순서를 맞춰야 하는 경우에 유용함
 */
public class T16_WaitAndNotify {
	public static void main(String[] args) throws InterruptedException {
		MyMonitorLock myLock = new MyMonitorLock();

		Thread consumer = new Thread(new Runnable() {
			@Override
			public void run() {
				myLock.consume();
			}
		});

		Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				myLock.produce();
			}
		});

		consumer.start();
		sleep(100); // consumer의 wait()을 먼저 하기 위함 -> producer의 notify()가 먼저 호출이 된다 consumer는 무한 대기가 됨(데드락)
		producer.start();

		consumer.join();
		producer.join();
		System.out.println("Main - end");
	}
}

class MyMonitorLock {
	public void consume() {
		System.out.println("MyMonitorLock.consume() - begin");
		synchronized (this) {
			try {
				System.out.println("MyMonitorLock.consume() - before wait()");
				wait();
				System.out.println("MyMonitorLock.consume() - after wait()");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("MyMonitorLock.consume() - end");
	}

	public void produce() {
		System.out.println("MyMonitorLock.produce() - begin");
		synchronized (this) {
			System.out.println("MyMonitorLock.produce() - notify()");
			notify();
		}
		System.out.println("MyMonitorLock.produce() - end");
	}
}
