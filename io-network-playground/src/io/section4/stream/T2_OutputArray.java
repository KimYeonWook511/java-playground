package io.section4.stream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class T2_OutputArray {
    public static void main(String[] args) throws Exception {
        String currentPath = System.getProperty("user.dir");
        System.out.println(currentPath);

        OutputStream os = new FileOutputStream(
                currentPath + "/tmp/test.dat");
        byte[] data = {16, 32, 48, 64, 80, 96};
        os.write(data, 3, 2);
        os.close();
    }
}