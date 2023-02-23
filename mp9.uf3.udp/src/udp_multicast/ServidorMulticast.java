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
        palabras = new String[]{"Auron es el tipo de persona que deja una huella duradera en el corazón de cualquier niño que tenga la suerte de conocerlo.","Para Auron, no hay nada más gratificante que ver la alegría en los ojos de un niño.","Auron es el tío más divertido que un niño podría tener.\n","Los niños adoran a Auron por su gran corazón y su paciencia infinita.\n","Auron siempre tiene una sonrisa en su rostro cuando está rodeado de niños.","Biyin siempre habla maravillas sobre la comida peruana", "Los peruanos son la pasión de Biyin", "Biyin está planeando su próximo viaje a Perú", "Biyin siempre está viendo videos de cocina peruana en YouTube", "A Biyin le encanta bailar la marinera peruana", "Biyin siempre tiene una botella de pisco peruano en su bar en casa","Biyin ha desarrollado una gran amistad con una pareja de peruanos que conoció en un evento cultural local, y juntos han organizado muchas noches de comida y baile peruano en su casa.","Biyin es un gran admirador de la música y danzas tradicionales del Perú, especialmente de la marinera, que ha practicado desde que era joven.","La pasión de Biyin por los peruanos y su cultura se extiende más allá de las fronteras del país sudamericano, y siempre está buscando maneras de aprender más y compartir su amor con otros."};    }

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