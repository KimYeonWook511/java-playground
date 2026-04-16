package io.section6.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

public class T7_MultiAsyncFileChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(
            Paths.get(System.getProperty("user.dir") + "/tmp/async.txt"),
            StandardOpenOption.WRITE,
            StandardOpenOption.CREATE
        );

        String data = "AsynchronousFileChannel I/O sample ";

        CountDownLatch latch = new CountDownLatch(2);

        ByteBuffer buffer1 = ByteBuffer.wrap(data.getBytes());
        asyncChannel.write(buffer1, 1024, "Async write (1KB): ", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println(attachment + result + " bytes");
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("ERROR: " + exc.getMessage());
                latch.countDown();
            }
        });

        ByteBuffer buffer2 = ByteBuffer.wrap(data.getBytes());
        asyncChannel.write(buffer2, 0, "Async write (0KB): ", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println(attachment + result + " bytes");
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("ERROR: " + exc.getMessage());
                latch.countDown();
            }
        });

        System.out.println("Waiting...");
        latch.await(); // 0이 될 때까지 기다림
        System.out.println("End of main thread.");

        asyncChannel.close();
    }
}