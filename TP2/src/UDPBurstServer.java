
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPBurstServer {

    public static void main(String[] args) throws Exception {

        int N = 10000; // 预计接收10000个数据包
        int delay = args.length > 0 ? Integer.parseInt(args[0]) : 0; // 处理延迟
        boolean[] received = new boolean[N]; // 记录收到的编号
        int count = 0; // 已收到的不同数据包数量

        try (DatagramSocket socket = new DatagramSocket(8081)) {

            socket.setSoTimeout(30000); // 最多等待30秒收到第一条消息
            System.out.println("Server started on port 8081");
            System.out.println("Delay: " + delay + " ms"); // 显示实际延迟

            while (true) {

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(packet); // 接收数据
                } catch (SocketTimeoutException e) {
                    break; // 没有新消息，结束统计
                }

                socket.setSoTimeout(3000); // 后续最多等待3秒

                String msg = new String(packet.getData(), 0,
                        packet.getLength(), StandardCharsets.UTF_8);

                int number = Integer.parseInt(msg); // 获取消息编号

                if (number >= 0 && number < N && !received[number]) {
                    received[number] = true; // 标记已经收到
                    count++; // 统计不同数据包
                }

                Thread.sleep(delay); // 模拟服务器处理延迟
            }

            int lost = N - count; // 计算丢包数量
            double lossRate = lost * 100.0 / N; // 计算丢包率

            System.out.println("Sent: " + N);
            System.out.println("Received: " + count);
            System.out.println("Lost: " + lost);
            System.out.printf("Loss rate: %.2f%%%n", lossRate);
        }
    }
}
