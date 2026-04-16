package network.section8.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class T1_EchoClient {
    public static void main(String[] args) {
        BufferedReader consoleInput = null;
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            consoleInput = new BufferedReader(
                    new InputStreamReader(System.in));

            //1
            socket = new Socket("127.0.0.1", 20000); // server의 ip, port 주소
            System.out.println("*** Connected to server ***");

            //2
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            //3
            String msg;
            while ((msg = consoleInput.readLine()) != null) {
                writer.println(msg);
                if ("exit".equalsIgnoreCase(msg))
                    break;

                String response = reader.readLine();
                System.out.println("From server: " + response);
            }
        }
        catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            //4
            try {
                if (socket != null)         socket.close();
                if (consoleInput != null)   consoleInput.close();
                if (reader != null)         reader.close();
                if (writer != null)         writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}