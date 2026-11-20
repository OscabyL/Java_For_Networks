import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPBurstServer {

    public static void main(String[] args) throws Exception {

        int N = 100;
        int delay = args.length > 0 ? Integer.parseInt(args[0]) : 0;
        boolean[] received = new boolean[N];
        int count = 0;
        int duplicates = 0;

        try (DatagramSocket socket = new DatagramSocket(8081)) {

            socket.setReceiveBufferSize(4096);
            System.out.println("Server started on port 8081");
            System.out.println("Delay: " + delay + " ms");
            System.out.println("Receive buffer: " + socket.getReceiveBufferSize());

            while (true) {

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                if (count == 0) {
                    socket.setSoTimeout(30000);
                } else {
                    socket.setSoTimeout(3000);
                }

                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException e) {
                    break;
                }

                String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                int number = Integer.parseInt(msg.split(":")[0]);

                if (number >= 0 && number < N) {
                    if (!received[number]) {
                        received[number] = true;
                        count++;
                    } else {
                        duplicates++;
                    }
                }

                if (delay > 0) {
                    Thread.sleep(delay);
                }
            }

            int lost = N - count;
            double lossRate = lost * 100.0 / N;

            System.out.println("Sent: " + N);
            System.out.println("Received: " + count);
            System.out.println("Duplicates: " + duplicates);
            System.out.println("Lost: " + lost);
            System.out.printf("Loss rate: %.2f%%%n", lossRate);
        }
    }
}
