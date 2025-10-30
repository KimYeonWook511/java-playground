package section3;

import java.util.Scanner;

public class ThreadInterruptTest {
    static class MyThread extends Thread {
        private boolean exit = false; // true가 되는 일은 없음
        @Override
        public void run() {
            System.out.println("*** Begin ***");
            while (!exit) {
                try {
                    sleep(1000);
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
