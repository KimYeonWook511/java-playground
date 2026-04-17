package io.section6.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.nio.channels.CompletionHandler;

public class T5_AsyncFileChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(
            Paths.get(System.getProperty("user.dir") + "/tmp/async.txt"),
            StandardOpenOption.WRITE,
            StandardOpenOption.CREATE
        );

        String data = "AsynchronousFileChannel I/O sample ";

        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        CountDownLatch latch = new CountDownLatch(1);

        // 쓰기 요청에 해당됨. -> 바로 쓰는 것이 아님. 요청하는 것이라 바로 쓸 수도 있고, 아닐 수도 있음
        // "Async write: "는 attachment로 들어감
        asyncChannel.write(buffer, 0, "Async write: ", new CompletionHandler<Integer, String>() {
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