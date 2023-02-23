package udp_multicast;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.net.*;
import java.net.NetworkInterface;

public class ClienteMulticast {
    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;


    public ClienteMulticast(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        //netIf = NetworkInterface.getByName("enp1s0");
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[1200];

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning){
            packet = new DatagramPacket(receivedData, 1200);
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

    protected boolean getData(byte[] data, int length) {
        boolean ret = true;

        // Convertir el arreglo de bytes en una cadena de texto
        String texto = new String(data,0,length);

        // Dividir el texto en un arreglo de Strings utilizando el espacio en blanco como separador
        String[] palabras = texto.split("\\s+");

        // Verificar si hay al menos 8 palabras y mostrar el texto por consola
        if (palabras.length >= 8) {
            System.out.println(texto);
        }

        return ret;
    }

    public static void main(String[] args) throws IOException {
        ClienteMulticast cvel = new ClienteMulticast(5557, "224.0.11.111");
        cvel.runClient();
        System.out.println("Parat!");

    }

}
