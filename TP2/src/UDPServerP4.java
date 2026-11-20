
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;

public class UDPServerP4 {

    public static void main(String[] args) throws Exception {

        HashMap<String, Integer> lastNumbers = new HashMap<>(); // 保存客户端最新编号
        HashSet<String> receivedMessages = new HashSet<>(); // 记录已经收到的消息

        try (DatagramSocket socket = new DatagramSocket(8080)) {

            System.out.println("UDP Server started on port 8080");

            while (true) {

                byte[] buffer = new byte[1024]; // 接收缓冲区
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet); // 接收UDP消息

                String msg = new String(packet.getData(), 0,
                        packet.getLength(), StandardCharsets.UTF_8); // 转换字符串

                String[] parts = msg.split(":", 2); // 分开编号和消息
                int number = Integer.parseInt(parts[0]); // 获取消息编号

                String client = packet.getAddress() + ":" + packet.getPort(); // 客户端地址

                // Simulate 20% packet loss
                if (Math.random() < 0.2) {
                    System.out.println("Dropped packet: " + msg); // 故意丢弃消息
                    continue; // 不处理，也不发送ACK
                }

                // Check duplicates
                String key = client + ":" + number; // 客户端和编号组成唯一标识

                if (receivedMessages.contains(key)) {
                    System.out.println("Duplicate: " + msg); // 检测重复消息
                } else {
                    receivedMessages.add(key); // 记录第一次收到的消息

                    int expected = lastNumbers.getOrDefault(client, -1) + 1;

                    System.out.println("Received: " + msg);

                    if (number > expected) {
                        System.out.println("Missing sequence: " + expected);
                    } else if (number < expected) {
                        System.out.println("Out of order: " + number);
                    }

                    lastNumbers.put(client,
                            Math.max(number, expected - 1)); // 更新最新编号
                }

                // Simulate 20% ACK loss
                if (Math.random() < 0.2) {
                    System.out.println("Dropped ACK:" + number); // 故意不发送ACK
                    continue;
                }

                // Send ACK
                String ack = "ACK:" + number; // 创建确认消息
                byte[] ackData = ack.getBytes(StandardCharsets.UTF_8);

                DatagramPacket ackPacket = new DatagramPacket(
                        ackData, ackData.length,
                        packet.getAddress(), packet.getPort());

                socket.send(ackPacket); // 回复客户端

                System.out.println("Sent: " + ack);
            }
        }
    }
}
