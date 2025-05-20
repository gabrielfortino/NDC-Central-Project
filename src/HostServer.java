//package HostConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HostServer {
    // Simpan koneksi aktif ATM: IP -> Socket
    private static Map<String, Socket> atmConnections = new ConcurrentHashMap<>();
//    private static byte[] command = new byte[] { 0x02, 0x31, 0x1C, 0x33, 0x30, 0x30, 0x1C, 0x1C, 0x33, 0x00 }; //coba cari message sesuai spec untuk di send ke atm
//    private static byte[] commandconfigurationparameterload = new byte[] {0x34, 0x37, 0x33, 0x33, 0x30, 0x30, 0x31, 0x33, 0x31, 0x30, 0x30, 0x30, 0x30,
//            0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
//            0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x33, 0x30, 0x30, 0x30, 0x37, 0x30,
//            0x35, 0x30 };
    //sesuai dengan urutan architecture
    //itu message request
    public static void main(String[] args) throws IOException {
        int port = 6001;
        MessageSender Sender = new MessageSender();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("SERVER STARTED, ATM HOST SERVER LISTENING ON PORT : " + port);

        // Thread pool buat handle banyak ATM
        ExecutorService executor = Executors.newCachedThreadPool();

        while (true){
            Socket clientSocket = serverSocket.accept();
            String atmIP = clientSocket.getInetAddress().getHostAddress();
            System.out.println("ATM connected from IP : " +atmIP);
            atmConnections.put(atmIP, clientSocket);
            executor.submit(() -> handleATMConnection(atmIP,clientSocket));
            byte [] commandrequestconfigid = Sender.requestConfigIDMessage();
            sendCommand(atmIP, commandrequestconfigid); //ini ip atm nya ya
            byte [] commandconfigurationparameterload = Sender.configurationParameterLoadMessage();
            sendCommand(atmIP, commandconfigurationparameterload);
        }
    }

    private static void sendCommand(String atmIP, byte[] command){
        System.out.println("try to send command");
        Socket socket = atmConnections.get(atmIP);
        if (socket != null && !socket.isClosed()) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(command);
                out.flush();
                System.out.println("Command sent to ATM " + atmIP);
            } catch (IOException e) {
                System.out.println("Failed to send command to " + atmIP);
            }
        } else {
            System.out.println("ATM not connected: " + atmIP);
        }
    }

    private static void loadDataTable(String atmIP, byte[] command){

    }

    private static void handleATMConnection(String atmIP, Socket socket) {
        try (
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
        ) {
            NDCParser ndcParser = new NDCParser();
            byte[] buffer = new byte[1024];
            int read;

            while ((read = in.read(buffer)) != -1) {
                // Tampilkan pesan dari ATM
                System.out.println("From ATM " + atmIP + ": ");
                for (int i = 0; i < read; i++) {
                    System.out.printf("%02X ", buffer[i]);
                    ndcParser.messageParser(buffer);
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("ATM disconnected: " + atmIP);
        } finally {
            atmConnections.remove(atmIP);
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }


}
