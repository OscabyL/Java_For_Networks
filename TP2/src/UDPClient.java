
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

            while ((message = reader.readLine()) != null) {
                byte[] data = message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet =
                        new DatagramPacket(data, data.length, address, port);

                socket.send(packet);
            }
        }
    }
}
