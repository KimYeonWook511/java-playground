package section2;

public class MyRunnableTest {
	public static void main(String[] args) {
		System.out.println("main() - begin");
		Runnable myRunnable = new MyRunnable();
		Thread thread = new Thread(myRunnable);
		thread.start();

		try{ Thread.sleep(500); } catch (Exception e) {}
		System.out.println("main() - end");
	}

	static class MyRunnable implements Runnable {
		public void run() {
			System.out.println("section2.MyRunnable.run() - begin");
			System.out.println("section2.MyRunnable.run() - end");
		}
	}
}
