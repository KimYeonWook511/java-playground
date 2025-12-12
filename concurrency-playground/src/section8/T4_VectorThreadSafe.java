package section8;

import java.util.List;
import java.util.Vector;

/**
 *  Vector는 Thread-Safe 하다!! -> ArrayList의 동시성 보장 버전이 Vector
 *  즉, 멀티 스레드에서도 정상적으로 동기화되어 동작함
 */
public class T4_VectorThreadSafe {
	public static void main(String[] args) throws InterruptedException {
		List<String> list = new Vector<String>();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("T1 - begin");
				for (int i = 0; i < 1000000; i++) {
					list.addLast("Test" + i);
				}
				System.out.println("T1 - end");
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("T2 - begin");
				for (int i = 0; i < 1000000; i++) {
					list.add("Data" + i);
				}
				System.out.println("T2 - end");
			}
		});

		long beginTime = System.currentTimeMillis();
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		long endTime = System.currentTimeMillis();

		System.out.println("Vector: " + list.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");
	}
}
