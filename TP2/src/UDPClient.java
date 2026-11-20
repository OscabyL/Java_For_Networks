import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class UDPClient {

    public static void main(String[] args) throws Exception {

        String host = args.length > 0 ? args[0] : "localhost";
        int port = 8888;

        InetAddress address = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            String message;

            //Test zone for try the buffer size management
            //int[] listSizeByte = {1000, 10000, 65507};
/*
            for (int i = 0; i < listSizeByte.length; i++){
                byte[] datatest = "A".repeat(listSizeByte[i]).getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet1 = new DatagramPacket(datatest, datatest.length, address, port);
                socket.send(packet1);
            }

 */


            while ((message = reader.readLine()) != null) {
                byte[] data = message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

                socket.send(packet);
            }
        }
    }
}
