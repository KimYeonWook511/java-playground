package io.section6.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * nio 기반 동기 모드 입/출력
 * stream당 channel을 할당함 (기존 파일 스트림에 대해 채널을 생성해 사용하는 구조)
 * 파일 입/출력을 매핑된 버퍼를 통해 쉽고 효율적으로 처리할 수 있도록 지원함
 * 메모리 매핑 파일(MMF) 지원 (파일을 메모리로 추상화함)
 * zero-copy 지원!!!
 */
public class T2_FileStreamChannel {
    private static final String targetPath = System.getProperty("user.dir") + "/tmp/nioTest.dat";
    private static FileOutputStream fos;
    private static FileInputStream fis;
    private static FileChannel writeChannel;
    private static FileChannel readChannel;

    private static void InitChannels() throws Exception {
        fos = new FileOutputStream(targetPath);
        writeChannel = fos.getChannel();
        fis = new FileInputStream(targetPath);
        readChannel = fis.getChannel();
    }

    private static void readData() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int length = readChannel.read(buffer); // 재호출 시 읽은 곳부터 이어서 읽게 됨.
        if(length > 0) {
            byte[] data = new byte[length];
            buffer.get(0, data);
            System.out.println("readData(): " + new String(data));
        }
    }

    private static void writeData(String data) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        writeChannel.write(buffer); // File에 대한 쓰기
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            InitChannels();
            String input;
            for(input = scanner.nextLine();
                !input.equals("exit"); input = scanner.nextLine()) {
                writeData(input);
                readData();
            }
            readChannel.close();
            writeChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}