package io.section4.stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * tinyWrite 예제
 */
public class T4_NonBufferedWrite {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        try {
            OutputStream os = new FileOutputStream(
                    currentPath + "/tmp/test.dat");
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1024 * 1024; i++)
                os.write(0xFF);
            os.close();
            long endTime = System.currentTimeMillis();
            System.out.println("Duration(ms): " + (endTime - startTime));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}