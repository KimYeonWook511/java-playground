package network.section9.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 서버에 사용되는 핵심 요소
 * Channel : 파일(혹은 소켓) 스트림에 대한 채널을 생성해 입/출력
 * Buffer : 채널에 연결된 메모리 버퍼
 * Selector : 채널(파일)들의 입/출력 요구를 감시
 *
 * I/O 와 처리를 분리해야 더 효율적임!!! (EventQueue 같은 것을 사용)
 * TCP Buffer에 쌓인 I/O를 빠르게 Application에서 올려줘야 다음 I/O도 계속 받을 수 있음
 * (이 코드에서는 분리하지 않음. 처리 부분 -> sendMessageAll)
 */
public class T1_SelectorChatServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int listenPort = 20000;
        Selector selector;
        ServerSocketChannel serverChannel;

        try {
            //1
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(listenPort));
            serverChannel.configureBlocking(false); // Non-Blocking 처리하겠다고 설정
            serverChannel.register(selector, SelectionKey.OP_ACCEPT); // 채널을 selector에 등록, select key는 accept로 (write, read, accept)
            System.out.println("NIO Chat Server start... ");
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //2
            while (true) {
                selector.select(); // 등록한 채널들에 대해 이벤트 대기 (감시)

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    //3 - accept (연결 처리)
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel clientSocket = server.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ); // selector에 read로 등록
                        System.out.println("New client connected...");
                        System.out.println("[" +
                                clientSocket.getRemoteAddress() + "]");
                    }

                    //4 (읽기 처리)
                    else if (key.isReadable()) {
                        //4-1
                        SocketChannel client = (SocketChannel) key.channel();
                        buffer.clear(); // 버퍼를 재활용 하기 (이전에 읽은 데이터를 초기화)
                        int bytesRead;

                        try {
                            //5
                            bytesRead = client.read(buffer);
                        } catch (IOException e) {
                            //5-1
                            System.out.println("Client terminated: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        //5-2 (client가 close를 call한 경우)
                        if (bytesRead == -1) {
                            System.out.println("Client disconnected: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        //5-3
                        buffer.flip();
                        String msg = new String(buffer.array(), 0, buffer.limit()).trim();
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Client exit command received: " + client);
                            key.cancel();
                            client.close();
                            continue;
                        }

                        //6
                        System.out.println("received[" + client.getRemoteAddress() + "]: " + msg);
                        sendMessageAll(selector, client, msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessageAll(Selector selector,
                  SocketChannel sender, String msg) throws IOException {
        ByteBuffer msgBuffer = ByteBuffer.wrap((msg + "\n").getBytes());

        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel) {
                if (channel.equals(sender)) continue; // 나 자신은 스킵
                SocketChannel target = (SocketChannel) channel;
                target.write(msgBuffer);
            }
        }
    }
}