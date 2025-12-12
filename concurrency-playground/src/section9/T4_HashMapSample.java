package section9;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *  Map 인터페이스 클래스
 *  - Map은 키(key)+값(value)로 구성되는 Map.Entry 객체를 비선형 구조로 관리함
 *  - 기본적으로 키는 중복 X, 값은 중복 O -> 즉, 키가 같다면 객체는 하나만 존재할 수 있음 (덮어 씌워짐)
 *  - HashMap은 Thread-Safe X
 *  - Hashtable은 Thread-Safe O
 */
public class T4_HashMapSample {
	public static void main(String[] args) {
		// Map<String, String> map = new HashMap<>();
		Map<String, String> map = new Hashtable<>();

		map.put("Tester1", "1111-1111");
		map.put("Tester2", "2222-2222");
		map.put("Tester3", "3333-3333");
		map.put("Tester1", "1111-1111");

		System.out.println("size(): " + map.size());
		System.out.println("----------------------------");

		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			System.out.println(key + "\t" + value);
		}
		System.out.println("----------------------------");
	}

	private static class UserData {
		String name;
		String phone;

		UserData (String name, String phone) {
			this.name = name;
			this.phone = phone;
		}
	}
}
