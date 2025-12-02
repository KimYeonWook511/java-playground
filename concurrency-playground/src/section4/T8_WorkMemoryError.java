package section4;

/**
 *  스레드는 작업 메모리를 가진다.
 *  작업 메모리와 메인 메모리의 동기화를 다룰줄 알아야 한다.
 *  [동기화가 되는 조건]
 *   - 명시적 동기화 (synchronized, volatile)
 *   - Thread.start(), join() 호출
 *   - Lock, Atomic 클래스 사용
 *   - 클래스 로딩 과정에서 정적 변수 초기화 시
 *   - 기타 JVM이 정한 최적화, 동기화 기준 충족 시
 */
public class T8_WorkMemoryError {

	static boolean exitFlag = false;

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
			while(!exitFlag) {
				++counter;
				// System.out.println(exitFlag); // println에는 synchronized가 있어서 JVM이 메인메모리와 작업메모리를 동기화 함
			}

			System.out.println("MyThread.run() - end, exitFlag: " + exitFlag);
			System.out.println("MyThread.run() - end, counter: " + counter);
		}
	}

}
