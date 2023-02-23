package udp_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class ServidorJuego {
    DatagramSocket socket;
    int port, fi;
    SecretNum ns;
    boolean acabat;

    public ServidorJuego(int port, int max) {
        try {
            socket = new DatagramSocket(port);
            System.out.printf("Puerto del servidor: " + port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        ns = new SecretNum(max);
        acabat = false;
        fi=-1;
    }

    public void runServer() throws IOException{
        byte [] receivingData = new byte[4];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port indefinidament
        while(!acabat){

            //creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 4);
            //espera de les dades
            socket.receive(packet);
            //processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
            //obtenció de l'adreça del client
            clientIP = packet.getAddress();
            //obtenció del port del client
            clientPort = packet.getPort();
            //creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            //enviament de la resposta
            socket.send(packet);
        }
        socket.close();
    }

    private byte[] processData(byte[] data, int length) {
        int nombre = ByteBuffer.wrap(data).getInt();
        System.out.println("rebut->"+nombre);
        fi = ns.comprova(nombre);
        if(fi==0) acabat=true;
        byte[] resposta = ByteBuffer.allocate(4).putInt(fi).array();
        return resposta;
    }

    public static void main(String[] args) throws SocketException, IOException {
        ServidorJuego sAdivina = new ServidorJuego(5556, 100);

        try {
            sAdivina.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Servidor cerrado!");
    }
}

class SecretNum {
    private int num;

    public SecretNum() {
        num = 0;
    }

    public SecretNum(int n) {
        pensa(n);
    }

    public void pensa(int max) {
        setNum((int) ((Math.random()*max)+1));
        //System.out.println("He pensat el " + getNum());
    }

    public int comprova(int n) {
        if(num==n) return 0;
        else if(num<n) return 1;
        else return 2;
    }


    public int getNum() {
        return num;
    }

    private void setNum(int num) {
        this.num = num;
    }
}