package io.section6.mmf;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MMF (Memory Mapped File)
 * 파일을 메모리로 추상화 하기 (MappedByteBuffer 클래스)
 * 파일을 메모리(Buffer) 처럼 사용함
 * 대용량 로그 처리시 유용 (OS 수 페이지 캐싱)
 * 파일의 용량이 클 경우 OOM 발생할 수 있으니, 일부만 메모리로 매핑해 처리
 * 운영체제에 의존적인 경향이 있음
 * Unmap은 GC가 수행되면서 이루어짐!!!
 */
public class T8_MemoryMappedFile {
    private static final String targetPath = System.getProperty("user.dir") + "/tmp/mmfTest.dat";

    private static void writeData(String data) throws Exception {
        FileOutputStream fos = new FileOutputStream(targetPath);
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        channel.write(buffer);
        channel.close();
    }

    public static void main(String[] args) {
        try {
            writeData("log test: memory mapped file sample");

            FileChannel channel;
            RandomAccessFile file = new RandomAccessFile(targetPath, "rw");
                channel = file.getChannel();

            long size = channel.size();
            MappedByteBuffer buffer = channel.map(
                    FileChannel.MapMode.READ_ONLY, 0, size);

            byte[] data = new byte[(int)size];
            buffer.get(data);
            System.out.println(new String(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}