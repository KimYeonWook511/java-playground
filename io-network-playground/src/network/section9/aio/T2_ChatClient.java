package network.section9.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class T2_ChatClient {
    static class ChatMsgReceiver implements Runnable {
        private final BufferedReader serverReader;

        public ChatMsgReceiver(BufferedReader serverReader) {
            this.serverReader = serverReader;
        }

        @Override
        public void run() {
            try {
                String serverMsg;
                while ((serverMsg = serverReader.readLine()) != null) {
                    System.out.println("From server: " + serverMsg);
                }
            } catch (IOException e) {
                System.out.println("*** Disconnected from server ***");
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader consoleInput = null;
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            consoleInput = new BufferedReader(
                    new InputStreamReader(System.in));

            //1
            socket = new Socket("127.0.0.1", 20000);
            System.out.println("*** Connected to server ***");

            //2
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            //3
            Thread thread = new Thread(new ChatMsgReceiver(reader));
            thread.start();

            //4
            String msg;
            while ((msg = consoleInput.readLine()) != null) {
                if ("exit".equalsIgnoreCase(msg))
                    break;
                writer.println(msg);
            }
        }
        catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            //5
            try {
                if (socket != null)         socket.close();
                if (consoleInput != null)   consoleInput.close();
                if (reader != null)         reader.close();
                if (writer != null)         writer.close();
                System.out.println("Exit");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}