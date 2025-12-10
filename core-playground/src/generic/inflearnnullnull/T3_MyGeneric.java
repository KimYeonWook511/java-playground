package generic.inflearnnullnull;

/**
 *  Generic을 활용한 ADT
 */
public class T3_MyGeneric {
	public static void main(String[] args) {
		MyDataGeneric<Integer> myInt = new MyDataGeneric<Integer>();
		myInt.set(5);
		int data = myInt.get();
		System.out.println(data);

		MyDataGeneric<String> myString  = new MyDataGeneric<String>();
		myString.set("Hello");
		String result = myString.get();
		System.out.println(result);
	}

	private static class MyDataGeneric<T> {
		private T data;
		public T get() {
			return data;
		}
		public void set(T param) {
			data = param;
		}
	}
}
