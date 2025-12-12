package section9;

import java.util.HashSet;
import java.util.Objects;

/**
 *  Set의 equals()와 hashCode()의 동작 학습
 *  - hashCode()를 재정의 하지 않는다면 Object의 hashCode()가 호출되어 객체의 메모리 주소를 기반으로 한 정수값을 반환하게 된다.
 *  - hashCode()는 Set에 add()할때 호출된다.
 *  - 이후 해당 인덱스에 객체가 존재할 경우 equals()를 호출해 비교한다.
 */
public class T3_SetEquals {
	public static void main(String[] args) {
		HashSet<UserData> set = new HashSet<>();
		set.add(new UserData("Tester", "1234-1234"));
		set.add(new UserData("Tester", "1234-1234"));
		set.add(new UserData("Tester", "1234-1234"));
		set.add(new UserData("Tester", "1234-12345"));

		System.out.println("size(): " + set.size());
		System.out.println("-------------------------------");

		set.clear();
		set.add(new UserDataHashCode("Tester", "1234-1234"));
		set.add(new UserDataHashCode("Tester", "1234-1234"));
		set.add(new UserDataHashCode("Tester", "1234-1234"));
		set.add(new UserDataHashCode("Tester", "1234-12345"));

		System.out.println("size(): " + set.size());
	}

	private static class UserData {
		private Object obj;

		UserData(String name, String phone) {
			this.name = name;
			this.phone = phone;
		}
		String name;
		String phone;

		@Override
		public boolean equals(Object obj) {
			System.out.println("equals()");
			this.obj = obj;
			if(obj == null)
				return false;
			UserData user = (UserData)obj;
			return name.equals(user.name) && phone.equals(user.phone);
		}

		// @Override
		// public int hashCode() {
		// 	System.out.println((name + phone).hashCode());
		// 	return (name + phone).hashCode();
		// }
	}

	private static class UserDataHashCode extends UserData {
		UserDataHashCode(String name, String phone) {
			super(name, phone);
		}

		@Override
		public int hashCode() {
			// System.out.println((name + phone).hashCode());
			// return (name + phone).hashCode();
			System.out.println(Objects.hash(name, phone));
			return Objects.hash(name, phone);
		}
	}
}
