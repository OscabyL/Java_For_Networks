
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UDPClientP4 {

    public static void main(String[] args) throws Exception {

        InetAddress address = InetAddress.getByName("127.0.0.1"); // 服务器地址
        int port = 8080; // 服务器端口
        int number = 0; // 消息编号

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            socket.setSoTimeout(2000); // ACK等待时间为2秒

            String message;

            while ((message = reader.readLine()) != null) {

                String data = number + ":" + message; // 添加编号
                byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packet = new DatagramPacket(
                        bytes, bytes.length, address, port);

                boolean received = false; // 是否收到ACK

                for (int i = 1; i <= 3; i++) {

                    socket.send(packet); // 发送消息
                    System.out.println("Sent: " + data + " Attempt: " + i);

                    try {
                        byte[] buffer = new byte[1024];
                        DatagramPacket ackPacket =
                                new DatagramPacket(buffer, buffer.length);

                        socket.receive(ackPacket); // 等待ACK

                        String ack = new String(
                                ackPacket.getData(), 0,
                                ackPacket.getLength(), StandardCharsets.UTF_8);

                        if (ack.equals("ACK:" + number) &&
                                ackPacket.getAddress().equals(address) &&
                                ackPacket.getPort() == port) {

                            System.out.println("Received: " + ack);
                            received = true;
                            break; // 收到正确ACK，停止重传
                        }

                    } catch (SocketTimeoutException e) {
                        System.out.println("Timeout! Retrying...");
                    }
                }

                if (!received) {
                    System.out.println("Error: No ACK after 3 attempts");
                }

                number++; // 下一条消息编号
            }
        }
    }
}
