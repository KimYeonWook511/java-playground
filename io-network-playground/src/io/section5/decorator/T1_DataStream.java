package io.section5.decorator;

import java.io.*;

public class T1_DataStream {
    static String currentPath = System.getProperty("user.dir");

    public static void makeFile() throws Exception{
        FileOutputStream os = new FileOutputStream(
                currentPath + "/tmp/test.dat");
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeDouble(3.14);
        dos.writeInt(-50);
        dos.writeUTF("Hello world");
        dos.close();
    }

    public static void main(String[] args) throws Exception{
        makeFile();
        FileInputStream is = new FileInputStream(
                currentPath + "/tmp/test.dat");
        DataInputStream dis = new DataInputStream(is);

        System.out.println(dis.readDouble());
        System.out.println(dis.readInt());
        System.out.println(dis.readUTF());
        dis.close();

//        double value = 3.14;
//        long bits = Double.doubleToRawLongBits(value);
//        System.out.printf("0x%016X\n", bits);
    }
}