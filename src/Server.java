import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class Server {

    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    Lista simulator = new Lista(0);

    public Server(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = simulator.agafaVelocitat().getBytes(StandardCharsets.UTF_8);
            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }


        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {

        Server srvVel = new Server(5557, "224.0.11.111");
        srvVel.runServer();
        System.out.println("Parat!");

    }
}
