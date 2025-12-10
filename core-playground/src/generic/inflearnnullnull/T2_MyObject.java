package generic.inflearnnullnull;

/**
 *  T1의 예제를 Object로 한다면 set은 문제 없음
 *  다만, get 할 때 타입을 확실히 알아야 캐스팅이 가능하다는 단점이 존재
 */
public class T2_MyObject {
	public static void main(String[] args) {
		MyDataObj myInt = new MyDataObj();
		myInt.set(5);
		int data = (int)myInt.get();
		System.out.println(data);

		MyDataObj myString  = new MyDataObj();
		myString.set("Hello");
		String result = (String)myString.get();
		System.out.println(result);
		//double dbl = (double)myString.get();
		//System.out.println(dbl);
	}

	private static class MyDataObj {
		private Object data;
		public Object get() {
			return data;
		}
		public void set(Object param) {
			data = param;
		}
	}
}
