package udp_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Random;

public class ServidorMulticast {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;

    String[] palabras;

    public ServidorMulticast(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        palabras = new String[]{"Hola buenas tardes!", "Adiós, me voy a casa de mi tía Belén", "Saludos a toda tu família, sobretodo a tu padre :D", "Perro", "Mi compañero Sergio es muy cabezón y pesado.", "El profe nos ha querido meter en un concurso de programación"};
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;
        Random random = new Random();

        while(continueRunning){
            int indice = random.nextInt(palabras.length);
            String palabraAleatoria = palabras[indice];

            byte[] bytesDelMensaje = palabraAleatoria.getBytes();

            sendingData = ByteBuffer.allocate(bytesDelMensaje.length).put(bytesDelMensaje).array();

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
        //Canvieu la X.X per un número per formar un IP.
        //Que no sigui la mateixa que la d'un altre company
        ServidorMulticast server = new ServidorMulticast(5557, "224.0.11.111");
        server.runServer();
        System.out.println("Parado!");

    }

}