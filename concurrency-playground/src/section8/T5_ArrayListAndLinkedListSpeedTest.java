package section8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 *  이 예제의 Vector는 단일 스레드라서 Lock 경쟁이 없어 ArrayList와 성능 차이가 적다
 *  -> 심지어 ArrayList는 Capacity를 1.5배씩 증가시키기 때문에 grow()가 더 많이 발생해서 더 느림
 *  -> Vector는 Capacity를 2배씩 증가시킴 (ArrayList보다 비교적 grow()를 덜 호출함)
 *  -> 이렇게 설계한 이유는 Vector는 가급적 꽉 차는 경우가 적어서 그렇다고 함
 */
public class T5_ArrayListAndLinkedListSpeedTest {
	public static void main(String[] args) throws InterruptedException {
		List<String> linkedList = new LinkedList<String>();
		List<String> arrayList = new ArrayList<String>();
		List<String> vector = new Vector<>();

		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < 200000; i++)
			linkedList.add(0, "Test" + i);
		long endTime = System.currentTimeMillis();
		System.out.println("LinkedList: " + linkedList.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		for (int i = 0; i < 200000; i++)
			arrayList.add(0, "Test" + i);
		endTime = System.currentTimeMillis();
		System.out.println("ArrayList: " + arrayList.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");

		beginTime = System.currentTimeMillis();
		for (int i = 0; i < 200000; i++)
			vector.add(0, "Test" + i);
		endTime = System.currentTimeMillis();
		System.out.println("Vector: " + vector.size());
		System.out.println("Duration: " + (endTime - beginTime) + " ms");
	}
}
