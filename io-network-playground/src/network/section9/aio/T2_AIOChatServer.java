package network.section9.aio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * aio 기반 서버의 특징
 * Java AIO(AsynchronousSocketChannel)는 윈도우 환경에서는 IOCP, 리눅스 환경에서는 epoll 구조로 구현
 * OS 수준 비동기 매커니즘을 활용할 수 있는 구조
 * nio는 최적화를 한 것이라면 aio는 os를 활용한 최적화
 * nio 흐름
 *  - select() 에서 block
 *  - 이벤트가 발생하면 깨어남
 *  - 이벤트를 발생시킨 key(ready 상태인 key)를 찾기 위해 전체를 순회
 *  - selectedKey()로 이벤트를 발생시킨 key(ready 상태인 key)들을 가져와 직접 accept, read 처리
 * aio 흐름 (이벤트를 발생시킨 소켓을 골라서 os가 알려줌)
 *  - AsynchronousServerSocketChannel를 이용하며, accept() 호출 시 즉시 반환됨
 *  - 실제 연결을 받을 때 Callback 되는 구조이며, 호출 자체는 시스템에 등록함 (CompletionHandler라는 Callback Interface 이용)
 *  - CompletionHandler의 completed(), failed()를 호출하는 것은 JVM이 제공하는 NIO.2 비동기 채널 구현체 내부에서 함
 *  - 시스템 수준에서 스레드들을 생성하고 Pool에서 관리하므로 응답성이 높음
 *  - 블로킹 없이 많은 수(천 단위)의 클라이언트 연결을 처리할 수 있음
 * 즉, aio는 nio에서 select() 단계인 감시 단계에서의 비효율을 줄임
 */
public class T2_AIOChatServer {
    private static final Set<AsynchronousSocketChannel> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws IOException {
        int port = 20000;
        //1
        // AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()
        //     .bind(new InetSocketAddress(port)); // 모든 인터페이스를 열음 -> 0.0.0.0:port
		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()
			.bind(new InetSocketAddress("127.0.0.1", port));
		System.out.println("AIO Chat Server start...");

        //2 시스템에 등록 (accept는 즉시 반환 -> CallBack)
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Void attachment) {
                try {
                    System.out.println("New client connected: " +
                            client.getRemoteAddress());
                } catch (IOException e) {
                    System.out.println("Failed to get client address");
                }

                //2-1
                server.accept(null, this); // accept 재등록 (이미 accept를 했기 때문에 재등록해 주어야 함)
                clients.add(client); // client 추가

                //2-2
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                readMessage(client, buffer);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Failed to accept: " + exc.getMessage());
            }
        });

        try {
            Thread.currentThread().join(); // main스레드가 종료가 안 되게 하기 위함
        } catch (InterruptedException e) {
            System.out.println("Server interrupted.");
        }
    }

    //3
    private static void readMessage(AsynchronousSocketChannel client, ByteBuffer buffer) {
		// 시스템에 등록
        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void attachment) {
                if (result == -1) {
                    disconnectClient(client);
                    return;
                }

                buffer.flip(); // 메시지 크기 만큼 자르기
                String msg = new String(buffer.array(), 0, buffer.limit()).trim();
                buffer.clear();

                try {
                    System.out.println("Received: " + msg + " from " + client.getRemoteAddress());
                } catch (IOException e) {
                    System.out.println("Unknown client message.");
                }

                if (msg.equalsIgnoreCase("exit")) {
                    disconnectClient(client);
                    return;
                }

                sendMessageAll(client, msg);
                readMessage(client, buffer); // 재등록
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("Read failed: " + exc.getMessage());
                disconnectClient(client);
            }
        });
    }

    //4
    private static void sendMessageAll(AsynchronousSocketChannel sender, String msg) {
        for (AsynchronousSocketChannel client : clients) {
            if (!client.equals(sender) && client.isOpen()) {
                ByteBuffer buffer = ByteBuffer.wrap((msg + "\n").getBytes());
                client.write(buffer);
            }
        }
    }

    //5
    private static void disconnectClient(AsynchronousSocketChannel client) {
        try {
            System.out.println("Client disconnected: " + client.getRemoteAddress());
        } catch (IOException e) {
            System.out.println("Unknown client disconnected.");
        }
        clients.remove(client);
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Error closing client channel.");
        }
    }
}
