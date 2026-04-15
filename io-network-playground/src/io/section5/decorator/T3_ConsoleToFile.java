package io.section5.decorator;

import java.io.PrintStream;

public class T3_ConsoleToFile {
    public static void main(String[] args) throws Exception {
        System.out.println("Console: Hello world");

        PrintStream originalOut = System.out;

        String currentPath = System.getProperty("user.dir");
        PrintStream ps = new PrintStream(
                currentPath + "/tmp/test.txt");
        System.setOut(ps);
        System.out.println("test.txt: HELLO WORLD");
        ps.close();

        System.setOut(originalOut);
        System.out.println("End");
    }
}