package section2;

import java.util.Scanner;

public class MultiThreadUITest {
	static class MyThreadForIo extends Thread {
		@Override
		public void run() {
			System.out.println("* File I/O - start *, ID: " + threadId());
			for (int i = 10; i <= 100; i += 10) {
				System.out.printf("TID: %d - %d%%\n", threadId(), i);
				try{ Thread.sleep(1000); } catch (Exception e) {}
			}
			System.out.println("* File I/O - complite *");
		}
	}

	public static void main(String[] args) {
		int input = 0;
		while ((input = printMenu()) != 0) {
			if(input == 1) {
				/**
				 *  MyThreadForIo.run()의 코드 로직을 여기에 넣을 시 main(유저 스레드)은 해당 for문 동안 동작할 수 없음
				 */
				Thread thread = new MyThreadForIo();
				/**
				 * JVM은 모든 user thread가 종료되면 데몬 스레드의 실행 여부와 상관없이 종료됨
				 * 즉, main 스레드가 종료된 뒤 남아 있는 스레드가 모두 데몬이면, JVM은 즉시 종료
				 */
				//thread.setDaemon(true);
				thread.start();
			}
		}
		System.out.println("Bye~!");
	}

	static int printMenu() {
		System.out.println("[1]File\t[2]View\t[3]Edit\t[0]Exit");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
}
