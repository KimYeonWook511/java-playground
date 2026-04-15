package io.section4.stream;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class T8_ReaderString {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        try (Reader reader = new FileReader(
                currentPath + "/tmp/test.txt")) {
            char[] buffer = new char[20];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, read));
            }
        }
        catch (IOException ignored) {
        }
    }
}