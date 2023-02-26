import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class Cliente {

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;
    int[] cont = {0,0,0,0,0};
    int numpalabras =0;



    public Cliente(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[1024];

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, 1024);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected  boolean getData(byte[] data, int length) {
        boolean ret=true;

        int v = ByteBuffer.wrap(data).getInt();

        String recibido = new String(data, 0, length);

        System.out.println(recibido);
        if (recibido.equals("Hola")){
            cont[0]++;
        }else if (recibido.equals("Huevo")){
            cont[1]++;
        }else if (recibido.equals("Multicast")){
            cont[2]++;
        }else if(recibido.equals("Buenas")){
            cont[3]++;
        }else {
            cont[4]++;

        }
        numpalabras++;
        if (numpalabras == 10){
            System.out.println("Hola: " + cont[0] + "  " + "Huevo: " + cont[1] + "  " + "Multicast: " + cont[2] + "  " + "Buenas: " + cont[3]+ "  " + "Asegurado: " + cont[4]);
            ret = false;
        }


        return ret;
    }

    public static void main(String[] args) throws IOException {
        Cliente cvel = new Cliente(5557, "224.0.11.111");
        cvel.runClient();
        System.out.println("Parat!");

    }
}
