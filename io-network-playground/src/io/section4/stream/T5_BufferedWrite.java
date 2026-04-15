package io.section4.stream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class T5_BufferedWrite {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        try {
            OutputStream os = new FileOutputStream(
                    currentPath + "/tmp/test.dat");
            BufferedOutputStream bos = new BufferedOutputStream(os, 65536);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 1024 * 1024; i++)
                bos.write(0xFF);
            os.close();
            long endTime = System.currentTimeMillis();
            System.out.println("Duration(ms): " + (endTime - startTime));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}