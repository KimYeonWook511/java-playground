package io.section4.stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class T1_OutputStreamSample {
    public static void main(String[] args) throws Exception {
        String currentPath = System.getProperty("user.dir");
        System.out.println(currentPath);

        OutputStream os = new FileOutputStream(
                currentPath + "/tmp/test.dat");
        byte[] data = new byte[3];
        data[0] = 16;
        data[1] = 32;
        data[2] = 64;
        os.write(data);
        os.close();
    }
}