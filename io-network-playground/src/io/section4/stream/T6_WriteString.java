package io.section4.stream;

import java.io.FileWriter;

public class T6_WriteString {
    public static void main(String[] args) throws Exception {
        String currentPath = System.getProperty("user.dir");
        System.out.println(currentPath);

        FileWriter writer = new FileWriter(
                currentPath + "/tmp/test.txt");
        char[] string = {'H', 'e', 'l', 'l', 'o'};
        writer.write(string);
        writer.write(" world");
        writer.close();
    }
}