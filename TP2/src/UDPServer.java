
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    private int port;
    private boolean running = false;

    // Constructor with port
    public UDPServer(int port) {
        this.port = port;
    }

    // Default constructor
    public UDPServer() {
        this.port = 8080;
    }


    // Start server
    public void launch() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            running = true;
            System.out.println(this);

            while (true) {

                byte[] tempBuffer = new byte[65507];

                DatagramPacket packet = new DatagramPacket(tempBuffer, tempBuffer.length);

                socket.receive(packet);

                int size = packet.getLength();

                byte[] buffer;

                if (size <= 64) {
                    buffer = new byte[64];
                }
                else if (size <= 2048) {
                    buffer = new byte[2048];
                }
                else {
                    buffer = new byte[65507];
                }

                System.arraycopy(tempBuffer, 0, buffer, 0, size);
                String msg = new String(buffer, 0, size, StandardCharsets.UTF_8);
                System.out.println("Message size: " + size);
                System.out.println("Selected buffer: " + buffer.length);
                System.out.println(msg);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            running = false;
        }
    }


    @Override
    public String toString() {
        return "UDPServer: port=" + port +
                ", running=" + running;
    }


    public static void main(String[] args) {
        UDPServer server;

        if (args.length > 0) {
            server = new UDPServer(Integer.parseInt(args[0]));
        } else {
            server = new UDPServer();
        }

        server.launch();
    }
}