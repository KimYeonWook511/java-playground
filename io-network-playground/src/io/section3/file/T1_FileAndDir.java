package io.section3.file;

import java.io.File;
import java.io.IOException;

public class T1_FileAndDir {
    public static void main(String[] args) {
        String tmpPath = "./tmp";
        File targetDir = new File(tmpPath);

        if (targetDir.exists()) {
            System.out.println("디렉토리 존재");
        } else {
            if (targetDir.mkdir()) {
                System.out.println("디렉토리 생성 완료");
            } else {
                System.out.println("디렉토리 생성 실패");
            }
        }

        File testFile01 = new File(tmpPath + "/test01.txt");
        File testFile02 = new File(tmpPath + "/test02.txt");
        try {
            testFile01.createNewFile();
            testFile02.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] fileList = targetDir.list();
        for(String name : fileList)
            System.out.println(name);
    }
}