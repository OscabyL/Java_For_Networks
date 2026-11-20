
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPBurstClient {

    public static void main(String[] args) throws Exception {

        String host = args.length > 0 ? args[0] : "127.0.0.1"; // 服务器地址
        int N = 100; // 发送数量

        InetAddress address = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket()) {

            long start = System.currentTimeMillis(); // 记录开始时间

            for (int i = 0; i < N; i++) {

                String prefix = i + ":"; // 添加消息编号
                String msg = prefix + "A".repeat(256 - prefix.length()); // 生成256字节消息

                byte[] data = msg.getBytes(StandardCharsets.UTF_8); // 转换字节

                DatagramPacket packet = new DatagramPacket(
                        data, data.length, address, 8081);

                socket.send(packet); // 发送UDP数据包
            }

            long end = System.currentTimeMillis(); // 记录结束时间

            System.out.println("Destination: " + host);
            System.out.println("Sent: " + N + " packets");
            System.out.println("Packet size: 256 bytes");
            System.out.println("Send time: " + (end - start) + " ms");
        }
    }
}
