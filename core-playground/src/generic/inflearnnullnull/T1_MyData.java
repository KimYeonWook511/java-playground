package generic.inflearnnullnull;

/**
 *  Generic이 필요한 예시
 */
public class T1_MyData {
	public class Main {
		public static void main(String[] args) {
			MyDataInt myInt = new MyDataInt();
			myInt.set(5);
			System.out.println(myInt.get());

			MyDataString myString  = new MyDataString();
			myString.set("Hello");
			System.out.println(myString.get());
		}

		private static class MyDataInt {
			private int data;
			public int get() { return data; }
			public void set(int param) { data = param; }
		}

		private static class MyDataString {
			private String data;
			public String get() { return data; }
			public void set(String param) { data = param; }
		}
	}
}
