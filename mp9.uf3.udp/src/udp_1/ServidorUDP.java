package udp_1;

import java.io.IOException;
import java.net.*;

public class ServidorUDP {
    DatagramSocket socket;
    InetAddress clientIP;
    int port;

    public ServidorUDP() throws SocketException, UnknownHostException {
        port = 12345; // puerto en el que escucha el servidor
        socket = new DatagramSocket(port);
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;

        int clientPort;

        while(true) {
            DatagramPacket packet = new DatagramPacket(receivingData,1024);
            socket.receive(packet);
            clientIP = packet.getAddress();
            sendingData = processData(packet.getData(),packet.getLength());
            //Llegim el port i l'adreça del client per on se li ha d'enviar la resposta
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData,sendingData.length,clientIP,clientPort);
            socket.send(packet);
        }
    }

    private byte[] processData(byte[] data, int lenght) {
        String msg = new String(data,0,lenght);
        msg = msg.toUpperCase();
        //Imprimir el missatge rebut i retornar-lo
        System.out.printf("(%s) %s%n",clientIP,msg);
        return msg.getBytes();
    }

    public static void main(String[] args) {
        try {
            ServidorUDP server = new ServidorUDP();
            server.runServer();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}