package io.section6.nio;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * 단일 채널 입/출력
 * 단일 채널을 사용한다면 position의 위치를 잘 컨트롤 해야함!!
 */
public class T3_FileSingleChannel {
    private static final String targetPath = System.getProperty("user.dir") + "/tmp/single.dat";
    private static FileChannel channel;

    private static void InitChannels() throws Exception {
        channel = FileChannel.open( // 파일 생성
                Paths.get(targetPath), // 파일 경로
                StandardOpenOption.TRUNCATE_EXISTING, // 파일 사이즈를 0으로 만들기
                StandardOpenOption.CREATE, // 없으면 생성
                StandardOpenOption.READ,
                StandardOpenOption.WRITE);
    }

    private static void readData(int length) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        if(channel.read(buffer) > 0) {
            byte[] data = new byte[length];
            buffer.get(0, data);
            System.out.println("readData(): " + new String(data));
        }
    }

    private static int writeData(String data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        return channel.write(buffer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            InitChannels(); // 단일 I/O 채널 생성
            String input;
            for(input = scanner.nextLine();
                !input.equals("exit"); input = scanner.nextLine()) {
                int length = writeData(input);
                channel.position(channel.position() - length); // write를 하면서 position이 이동해 버리니 되돌리기
                readData(length);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}