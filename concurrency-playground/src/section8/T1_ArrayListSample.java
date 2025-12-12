package section8;

import java.util.ArrayList;

/**
 *  ArrayList는 Thread-Safe X -> 싱글 스레드에선 안전
 */
public class T1_ArrayListSample {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();

		list.add("Hello");
		list.add("World");
		list.add("Java");

		list.set(1, "TEST");

		System.out.println("List.size(): " + list.size());
		for(String data : list)
			System.out.println(data);

		list.remove(1);

		for(int i = 0; i < list.size(); ++i)
			System.out.println("Index: " + i + " " + list.get(i));
	}
}
