
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        String host = args.length > 0 ? args[0] : "localhost"; // 服务器地址
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080; // 端口

        InetAddress address = InetAddress.getByName(host); // 获取地址

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            String message;

            while ((message = reader.readLine()) != null) { // 读取输入
                byte[] data = message.getBytes(StandardCharsets.UTF_8); // 转换字节

                DatagramPacket packet =
                        new DatagramPacket(data, data.length, address, port);

                socket.send(packet); // 发送消息
            }
        }
    }
}
