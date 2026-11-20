import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MulticastReceiver {

    public MulticastReceiver() throws IOException{
    }

    public void launch() throws IOException{

        // We creat the socket and all its feature with the group
        MulticastSocket socket = new MulticastSocket(8888);
        InetAddress group = InetAddress.getByName("239.0.0.1");
        NetworkInterface netIf = NetworkInterface.getByName("eth0"); // your interface
        socket.joinGroup(new InetSocketAddress(group, 8888), netIf);

        // This is a counter that is usefull to check what happen when one of the receiver leave the group
        int message = 0;

        while(true){
            // This part is the same as the one in UDPServer
            byte[] buffer = new byte[65507];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            //message+=1;
            // When we use the previous line and the followings steps, the receiver leave the group after 3 received messages
            if (message==3){
                socket.leaveGroup(new InetSocketAddress(group, 8888), netIf);
            }

            socket.receive(packet);
            String msg = new String(buffer, 0, packet.getLength(), StandardCharsets.UTF_8);
            System.out.println(msg);
        }
    }



    public static void main(String[] args) throws IOException{
        MulticastReceiver newReceiver = new MulticastReceiver();
        newReceiver.launch();
    }
}
