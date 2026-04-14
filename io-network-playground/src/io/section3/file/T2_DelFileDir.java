package io.section3.file;

import java.io.File;

public class T2_DelFileDir {
    public static void main(String[] args) {
        String tmpPath = "./tmp";
        File targetDir = new File(tmpPath);

        if (targetDir.exists()) {
            File testFile01 = new File(tmpPath + "/test01.txt");
            File testFile02 = new File(tmpPath + "/test02.txt");
            testFile01.delete();
            testFile02.delete();

            if (targetDir.delete()) {
                System.out.println("디렉토리 삭제 성공");
            } else {
                // 재귀로 내부 파일 삭제 해야함
                System.out.println("디렉토리 삭제 실패 - 내부에 파일이 존재하면 디렉토리 제거 불가능");
            }
        }
    }
}