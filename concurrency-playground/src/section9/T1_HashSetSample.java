package section9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  HashSet은 Thread-Safe X
 *  Iterator(열거자)에서 데이터를 삭제해도 실제 Collection 즉 HashSet에서도 삭제됨
 */
public class T1_HashSetSample {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		System.out.println(set.add("Hello"));
		System.out.println(set.add("World"));
		System.out.println(set.add("Java"));
		System.out.println(set.add("Java") + "\n");
		System.out.println("size():" + set.size());
		System.out.println(set.contains("Java"));
		System.out.println("----------------------");

		for (String s : set) {
			System.out.println(s);
		}
		System.out.println("----------------------");

		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("----------------------");

		it = set.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
			it.remove();
		}
		System.out.println("set.size(): " + set.size());
	}
}
