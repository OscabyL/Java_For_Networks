
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPBurstServer {

    public static void main(String[] args) throws Exception {

        int N = 100; // 预计10000个数据包
        int delay = args.length > 0 ? Integer.parseInt(args[0]) : 0; // 处理延迟
        boolean[] received = new boolean[N]; // 记录收到的编号
        int count = 0; // 收到的数据包数量
        int duplicates = 0; // 重复数据包数量

        try (DatagramSocket socket = new DatagramSocket(8081)) {

            socket.setReceiveBufferSize(4096); // 请求减小系统接收缓冲区
            System.out.println("Server started on port 8081");
            System.out.println("Delay: " + delay + " ms");
            System.out.println("Receive buffer: " + socket.getReceiveBufferSize());

            while (true) {

                byte[] buffer = new byte[1024]; // 创建接收数组
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                if (count == 0) {
                    socket.setSoTimeout(30000); // 第一条消息最多等待30秒
                } else {
                    socket.setSoTimeout(3000); // 后续最多等待3秒
                }

                try {
                    socket.receive(packet); // 接收消息
                } catch (SocketTimeoutException e) {
                    break; // 超时后结束统计
                }

                String msg = new String(packet.getData(), 0,
                        packet.getLength(), StandardCharsets.UTF_8);

                int number = Integer.parseInt(msg.split(":")[0]); // 读取编号

                if (number >= 0 && number < N) {
                    if (!received[number]) {
                        received[number] = true; // 标记已收到
                        count++;
                    } else {
                        duplicates++; // 统计重复编号
                    }
                }

                if (delay > 0) {
                    Thread.sleep(delay); // 模拟处理延迟
                }
            }

            int lost = N - count; // 计算丢包数量
            double lossRate = lost * 100.0 / N; // 计算丢包率

            System.out.println("Sent: " + N);
            System.out.println("Received: " + count);
            System.out.println("Duplicates: " + duplicates);
            System.out.println("Lost: " + lost);
            System.out.printf("Loss rate: %.2f%%%n", lossRate);
        }
    }
}
