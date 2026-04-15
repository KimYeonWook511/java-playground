package io.section6.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class T4_ZeroCopy {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        String srcPath = currentPath + "/tmp/src.txt";
        String dstPath = currentPath + "/tmp/dst.txt";
        try {
            FileChannel src = new FileInputStream(srcPath).getChannel();
            FileChannel dst = new FileOutputStream(dstPath).getChannel();
            dst.transferFrom(src, 0, src.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}