package section3;

public class ExitFlagTest {
    public static boolean exitFlag = false; // Race Condition (하지만 read만 하기 때문에 해당 코드에선 다른 문제가 발생하지 않는 것)

    public static void main(String[] args) {
        System.out.println("main() - begin");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("MyThread.run() - begin");
                while(!exitFlag) {
                    try{ Thread.sleep(1); } catch (Exception e) {} // cpu 점유율 100%를 방지하기 위한 sleep
                }
                System.out.println("MyThread.run() - end");
            }
        });
        thread.start();

        System.out.println("exitFlag = true;");
        exitFlag = true;
        try{ Thread.sleep(500); } catch (Exception e) {}
        System.out.println("main() - end");
    }
}
