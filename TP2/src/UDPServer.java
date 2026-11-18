
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    private int port; // 服务器端口
    private boolean running = false; // 运行状态

    // Constructor with port
    public UDPServer(int port) {
        this.port = port;
    }

    // Default constructor
    public UDPServer() {
        this(8080);
    }

    // Start server
    public void launch() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            running = true;
            System.out.println(this);

            while (true) {
                byte[] buffer = new byte[1024]; // 接收缓冲区
                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                socket.receive(packet); // 接收消息

                String msg = new String(
                        packet.getData(), 0, packet.getLength(),
                        StandardCharsets.UTF_8);

                System.out.println(
                        packet.getAddress() + ":" +
                                packet.getPort() + " -> " + msg);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            running = false;
        }
    }

    // Display server information
    @Override
    public String toString() {
        return "UDPServer: port=" + port +
                ", running=" + running;
    }

    // Main method
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
