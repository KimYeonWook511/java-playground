package section9;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *  HashSet은 Thread-Safe 하지 않기 때문에 동기화가 되지 않음
 *  Collections.synchronizedSet()를 사용한다면 동기화가 되는 Set이 만들어짐
 */
public class T2_HashSetMultiThread {
	public static void main(String[] args) throws InterruptedException {
		Set<String> set = new HashSet<>(); // 동기화 X
		Set<String> syncSet = Collections.synchronizedSet(new HashSet<>()); // 동기화 O

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					set.add("TEST" + i);
					syncSet.add("TEST" + i);
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					set.add("DATA" + i);
					syncSet.add("DATA" + i);
				}
			}
		});

		t1.start(); t2.start();
		t1.join(); t2.join();

		System.out.println("set.size(): " + set.size());
		System.out.println("syncSet.size(): " + syncSet.size());
	}
}
