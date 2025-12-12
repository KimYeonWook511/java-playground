package section8;

import java.util.LinkedList;
import java.util.Vector;

/**
 *  LinkedList는 Thread-safe X
 *  Vector는 Thread-safe O
 */
public class T2_LinkedListAndVectorSample {
	public static void main(String[] args) throws InterruptedException {
		LinkedList<String> linkedList = new LinkedList<String>();
		Vector<String> list = new Vector<String>();

		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 200000; i++)
			linkedList.add(0, "Test" + i);
		long endTime = System.currentTimeMillis();
		System.out.println("LinkedList: " + linkedList.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		for (int i = 0; i < 200000; i++)
			list.add(0, "Test" + i);
		endTime = System.currentTimeMillis();
		System.out.println("Vector: " + list.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");
	}
}
