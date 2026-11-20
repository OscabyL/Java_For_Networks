import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    private int port;
    private boolean running = false;
    private int bufferSize;

    // The two followings methods are the constructors of the UDPserver
    public UDPServer(int port, int bufferSize) {
        this.port = port;
        this.bufferSize = bufferSize;
    }

    public UDPServer(int bufferSize) {
        this.port = 8080;
        this.bufferSize = bufferSize;
    }

    // We launch the serveur. It listen on a specific port the message
    public void launch() {

        try (DatagramSocket socket = new DatagramSocket(port)) {

            running = true;

            byte[] smallBuffer = new byte[64];
            byte[] largeBuffer = new byte[2048];
            byte[] hugeBuffer = new byte[65507];

            while (running) {
                // We listen and when a packet arrive we save it
                DatagramPacket packet = new DatagramPacket(hugeBuffer, hugeBuffer.length);

                socket.receive(packet);

                int size = packet.getLength();

                // In this part, we set up the choice of the size of the buffer
                byte[] buffer2;
                if (size <= 64) {
                    buffer2 = smallBuffer;
                }
                else if (size <= 2048) {
                    buffer2 = largeBuffer;
                }
                else {
                    buffer2 = hugeBuffer;
                }

                if (buffer2 != hugeBuffer) {
                    System.arraycopy(hugeBuffer, 0, buffer2, 0, size);
                }

                System.out.println("Buffer size: " + buffer2.length);
                System.out.println("Received bytes: " + size);

                String msg = new String(buffer2, 0, size, StandardCharsets.UTF_8);

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
        return "UDPServer: port=" + port + ", running=" + running;
    }


    public static void main(String[] args) {

        int port = 8080;
        int bufferSize = 65507;

        UDPServer server = new UDPServer(port, bufferSize);

        server.launch();
    }
    }
