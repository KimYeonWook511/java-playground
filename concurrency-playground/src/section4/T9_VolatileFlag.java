package section4;

/**
 *  volatile - 자바에서는 variable modifier(변수 한정자) 라고 함 (JLS 에서는 "Filed Modifier" 라고 부름)
 *  		 - 멀티 스레드 환경에서 여러 스레드가 접근하는 변수(메모리)에 대해 가시성(visibility) 제공
 */
public class T9_VolatileFlag {

	static volatile boolean exitFlag = false;

	public static void main(String[] args) throws InterruptedException{
		System.out.println("[main] begin");

		MyThread myThread = new MyThread();
		Thread t1 = new Thread(myThread, "TestThread");
		t1.start();

		Thread.sleep(100);
		exitFlag = true;
		Thread.sleep(2000);

		System.out.println("[main] end, exitFlag: " + exitFlag);
	}

	public static class MyThread implements Runnable {
		@Override
		public void run(){
			System.out.println("MyThread.run() - begin");
			int counter = 0;
			while(!exitFlag) { // Read -> 매번 동기화가 일어남
				++counter;
			}

			System.out.println("MyThread.run() - end, exitFlag: " + exitFlag);
			System.out.println("MyThread.run() - end, counter: " + counter);
		}
	}

}
