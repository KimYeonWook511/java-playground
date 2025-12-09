package section5;

import static java.lang.Thread.sleep;

import java.util.concurrent.locks.LockSupport;

/**
 *  LockSupport 클래스
 *  - 스레드 제어를 위한 유틸리티 클래스
 *  - 매우 가볍고 유연함 (나노초 단위를 사용하므로 더 섬세하게 조작 가능)
 *  - Lock이 필요 없음! - Lock Free (wait(), notify()는 synchronized 기반으로 작동)
 *  - Spin Lock 구현 시 불필요한 CPU 사용을 줄일 수 있고 성능도 향상 시킬 수 있음
 *  - unpark()이후 park()가 되어도 문제 없음
 */
public class T18_LockSupportParkUnpark {
	public static void main(String[] args) throws InterruptedException {
		Thread consumer = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("consumer - begin");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("\t*consumer - park()");
				LockSupport.park();
				System.out.println("consumer - end");
			}
		});

		Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("producer - begin");
				System.out.println("\t*producer - park()");
				LockSupport.park();
				System.out.println("producer - end");
			}
		});

		consumer.start();
		producer.start();

		sleep(100);
		System.out.println("*LockSupport.unpark()");
		LockSupport.unpark(consumer);
		LockSupport.unpark(producer);

		consumer.join();
		producer.join();
	}
}
