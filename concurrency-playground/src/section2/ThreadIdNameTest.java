package section2;

public class ThreadIdNameTest {
	static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println(getName() + " - begin");
			System.out.println("Thread ID: " + threadId());
			System.out.println(getName() + " - end");
		}
	}

	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();
		mainThread.setName("Main thread");
		System.out.println(mainThread.getName() + " - begin");
		System.out.println("Thread ID: " + mainThread.threadId());
		Thread thread = new MyThread();
		thread.setName("Worker thread");
		thread.start();

		try{ Thread.sleep(500); } catch (Exception e) {}
		System.out.println(mainThread.getName() + " - end");
	}
}
