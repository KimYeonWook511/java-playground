package section9;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *  List + Set을 이용한 데이터 관리
 *  - List 선형 구조
 *  - Set 비선형 구조
 *  - Set도 iterator를 이용하면 전체 순회가 가능하지만 열거자를 생성하는 것 또한 오버헤드가 발생함
 */
public class T5_ListAndSet {
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		Set<String> set = new TreeSet<>();
		long beginTime, endTime;

		beginTime = System.currentTimeMillis();
		int i = 0;
		for (i = 0; i < 10000000; i++)
			list.add("Test" + i);
		endTime = System.currentTimeMillis();
		System.out.println("list.add(): " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		i = 0;
		for (i = 0; i < 10000000; i++)
			set.add("Test" + i);
		endTime = System.currentTimeMillis();
		System.out.println("set.add(): " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		list.contains("Test" + (i - 1));
		endTime = System.currentTimeMillis();
		System.out.println("list.contains(): " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		set.contains("Test" + (i - 1));
		endTime = System.currentTimeMillis();
		System.out.println("set.contains(): " + (endTime - beginTime) + " ms");
	}
}
