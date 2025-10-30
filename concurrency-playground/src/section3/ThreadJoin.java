package section3;

/**
 * join()은 파라미터로 ms를 넣을 수 있음
 * 해당 시간만큼 기다리고 돌아오며, 생략 시 무한정 대기한다.
 */
public class ThreadJoin {
    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println(
                String.format("%s.run() - begin", Thread.currentThread().getName())
            );

            for(int i = 0; i < 5; ++i) {
                try {
                    System.out.println(
                        String.format("%s.run() - %d", Thread.currentThread().getName(), i)
                    );
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(
                String.format("%s.run() - end", Thread.currentThread().getName())
            );
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[3];
        for(int i = 0; i < 3; ++i) {
            threads[i] = new Thread(new MyThread(), "TestThread" + i);
            threads[i].start();
        }

        for(int i = 0; i < 3; ++i) {
            try {
                System.out.println(
                    String.format("** main -> %s.join() - begin", threads[i].getName())
                );
                threads[i].join(1000);
                System.out.println(
                    String.format("** main -> %s.join() - end", threads[i].getName())
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("main() - end");
    }
}
