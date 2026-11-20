import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class UDPClient {

    public static void main(String[] args) throws Exception {

        // We set up the settings of the CLient (where it will send the message)
        String host = "localhost";

        int port = 8888;

        InetAddress address = InetAddress.getByName("255.255.255.255");

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

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

                //The client listen the user to write a message and when we enter, we send the message over the
                byte[] data = message.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.setBroadcast(true);

                socket.send(packet);
            }
        }
    }
}
