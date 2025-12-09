package section5;

import java.util.concurrent.locks.LockSupport;

/**
 *  LockSupport 클래스
 *  - parkUntil(): 시간을 특정해서 그때까지 park가 되도록 함
 *  - parkNanos(): 나노초로 정밀한 제어를 할 수 있음 (스핀 락에서 유용하게 사용한다고 함)
 */
public class T19_LockSupportParkUntil {
	public static void main(String[] args) throws InterruptedException {
		Thread worker = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("worker - begin");
				System.out.println("\t*parkUntil(1000ms)");
				//sleep(1000);
				long wakeUpTime = System.currentTimeMillis() + 1000;
				LockSupport.parkUntil(wakeUpTime); // 시간을 특정해서 그때까지 park가 되도록 하기

				System.out.println("\t*parkNanos(1000ms)");
				LockSupport.parkNanos(1000 * 1000 * 1000); // 밀리초 - 마이크로초 - 나노초
				System.out.println("worker - end");
			}
		});

		worker.start();
		worker.join();
	}
}
