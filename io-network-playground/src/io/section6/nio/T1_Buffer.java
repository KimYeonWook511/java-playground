package io.section6.nio;

import java.nio.IntBuffer;

/**
 * Buffer, Channel, Selector
 */
public class T1_Buffer {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(100); // 배열하고 상당히 유사함. (new int[100])

        System.out.println("Start: " + buffer.position(0));
        for (int i = 1; i <= 50; i++)
            buffer.put(i * 10);

        System.out.println("Position: " + buffer.position());

        System.out.println(buffer.get(0));
        System.out.println(buffer.get(49));
        System.out.println(buffer.position(25));
        System.out.println(buffer.get(0));
    }
}