import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.InetSocketAddress;
import java.io.IOException;

public class MulticastReceiver {

    public MulticastReceiver() throws IOException{
    }

    public void launch() throws IOException{
        MulticastSocket socket = new MulticastSocket(8888);
        InetAddress group = InetAddress.getByName("239.0.0.1");
        NetworkInterface netIf = NetworkInterface.getByName("eth0"); // your interface
        socket.joinGroup(new InetSocketAddress(group, 8888), netIf);
    }



    public static void main(String[] args) throws IOException{
        MulticastReceiver newReceiver = new MulticastReceiver();
        newReceiver.launch();
    }
}
