package network.section8.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class T1_EchoServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null; // 접속 대기 소켓
        Socket clientSocket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        BufferedWriter bw = null;
        int listenPort = 20000;

        try {
            //1
            System.out.println("Server start...");
            serverSocket = new ServerSocket(listenPort);

            //2
            clientSocket = serverSocket.accept(); // block
            System.out.println("Client connected");

            //3
            InputStream inputStream = clientSocket.getInputStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            //4
            OutputStream outputStream = clientSocket.getOutputStream();
            // writer = new PrintWriter(outputStream, true);
            bw = new BufferedWriter(new OutputStreamWriter(outputStream));

            //5
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println("Received: " + msg);
                // writer.println(msg);
                bw.write(msg);
                bw.newLine(); // 개행이 없으면 client에서 계속 읽고 있음
                bw.flush();

                if ("exit".equalsIgnoreCase(msg))
                    break;
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            //4
            try {
                if (serverSocket != null)   serverSocket.close();
                if (clientSocket != null)   clientSocket.close();
                if (reader != null)         reader.close();
                // if (writer != null)         writer.close();
                if (bw != null)         bw.close();

                System.out.println("Server stop...");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}