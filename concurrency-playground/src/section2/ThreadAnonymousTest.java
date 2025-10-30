package section2;

public class ThreadAnonymousTest {
	public static void main(String[] args) {
		System.out.println("main() - begin");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("MyThread.run() - begin");
				System.out.println("MyThread.run() - end");
			}
		});
		thread.start();

		try {
			Thread.sleep(500);
		} catch (Exception e) {
		}
		System.out.println("main() - end");
	}
}
