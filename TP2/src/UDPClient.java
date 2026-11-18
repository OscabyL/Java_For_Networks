
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;

        InetAddress address = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            String message;

            //Test zone for try the buffer size management

            byte[] datatest = "test".getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet1 = new DatagramPacket(datatest, datatest.length, address, port);
            socket.send(packet1);

            byte[] datatest2 = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttest".getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet2 = new DatagramPacket(datatest2, datatest2.length, address, port);
            socket.send(packet2);

            byte[] datatest3 = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest".getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet3 = new DatagramPacket(datatest3, datatest3.length, address, port);
            socket.send(packet3);

            byte[] datatest4 = "A".repeat(65507).getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet4 = new DatagramPacket(datatest4, datatest4.length, address, port);
            socket.send(packet4);

            byte[] datatest5 = "A".repeat(65507).getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet5 = new DatagramPacket(datatest5, datatest5.length, address, port);
            socket.send(packet5);

            while ((message = reader.readLine()) != null) {
                byte[] data = message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

                socket.send(packet);
            }
        }
    }
}
