package network.section8.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class T2_MultiThreadEchoServer {
    static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private BufferedReader reader = null;
        private PrintWriter writer = null;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream = clientSocket.getOutputStream();
                writer = new PrintWriter(outputStream, true);

                String msg;
                while ((msg = reader.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(msg))
                        break;

                    System.out.println(msg);
                    writer.println(msg);
                }
            }
            catch (IOException ex) {
                System.out.println("Client exception: " + ex.getMessage());
            }
            finally {
                try {
                    if (clientSocket != null)   clientSocket.close();
                    if (reader != null)         reader.close();
                    if (writer != null)         writer.close();

                    System.out.println("Disconnected: " +
                        clientSocket.getInetAddress() + ":" +
                        clientSocket.getPort());
                }
                catch (IOException ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int listenPort = 20000;

        try {
            //1
            System.out.println("Server start...");
            serverSocket = new ServerSocket(listenPort);

            //2
            while (true) {
                //3
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected...");
                System.out.println("[" +
                        clientSocket.getInetAddress() + ":" +
                        clientSocket.getPort() + "]");

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
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
                System.out.println("Server stop...");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}