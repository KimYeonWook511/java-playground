package io.section4.stream;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class T7_ReaderChar {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        try (Reader reader = new FileReader(
                currentPath + "/tmp/test.txt")) {
            int data;
            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
        }
        catch (IOException ignored) {
        }
    }
}