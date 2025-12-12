package section8;

import static java.lang.Thread.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  ArrayList와 LinkedList는 Thread-Safe 하지 않다!
 *  즉, 멀티 스레드 환경에서 동시성 문제를 해결해야함
 */
public class T3_ListMultiThreadError {
	public static void main(String[] args) throws InterruptedException {
		List<String> list = new ArrayList<String>();
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

		t1.start();
		t2.start();
		sleep(10);
		t1.join();
		t2.join();
		System.out.println("ArrayList: " + list.size());
	}
}
