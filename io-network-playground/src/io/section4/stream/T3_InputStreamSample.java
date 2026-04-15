package io.section4.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class T3_InputStreamSample {
    public static void makeFile() throws Exception {
        String currentPath = System.getProperty("user.dir");
        OutputStream os = new FileOutputStream(
                currentPath + "/tmp/test.dat");
        // byte[] data = {'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] data = {16, 32, 64, 'A', 65, 'F'};
        os.write(data);
        os.close();
    }

    public static void main(String[] args) {
        try { makeFile(); }
        catch (Exception ignored) {}

        String currentPath = System.getProperty("user.dir");
        try (FileInputStream input = new FileInputStream(
                currentPath + "/tmp/test.dat")) {
            int data;
            while ((data = input.read()) != -1) { // EOF == -1
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}