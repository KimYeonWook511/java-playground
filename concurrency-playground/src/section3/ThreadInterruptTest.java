package section3;

import java.util.Scanner;

/**
 * interrupt()는 언제든 호출할 수 있으며, 스레드 객체 안에 있는 interrupted 플래그를 true로 바꿈
 * RUNNABLE (실행 중) - 즉시 중단되지 않음. 플래그만 true로 바뀜
 * WAITING / TIMED_WAITING (wait(), sleep(), join()) - 즉시 InterruptedException 발생 후 깨어남
 * BLOCKED (락 대기 중) - 즉시 반응 안 함 (락 획득 시 이후 코드에서 플래그 확인 가능)
 * TERMINATED - 아무 일도 일어나지 않음
 */
public class ThreadInterruptTest {
    static class MyThread extends Thread {
        private boolean exit = false; // true가 되는 일은 없음
        @Override
        public void run() {
            System.out.println("*** Begin ***");
            int count = 0;
            while (!exit) {
                try {
                    System.out.println("Waiting for thread interrupt... - " + count);
                    sleep(1000);
                    count++;
                } catch (InterruptedException e) {
                    System.out.println("* [InterruptedException] *");
                    break;
                }
            }

            System.out.println("***  End  ***");
        }
    }

    public static void main(String[] args) {
        int input = 0;
        Thread thread = null;
        while ((input = printMenu()) != 0) {
            if(input == 1) {
                if(thread == null) {
                    thread = new MyThread();
                    thread.start();
                }
            }
        }

        if(thread != null) {
            thread.interrupt();
        }
        System.out.println("bye");
    }

    static int printMenu() {
        System.out.println("[1]New thread\t[0]Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
