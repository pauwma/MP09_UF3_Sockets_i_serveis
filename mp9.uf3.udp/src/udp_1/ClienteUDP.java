package udp_1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    private DatagramSocket socket;
    private InetAddress ipServidor;
    private int puertoServidor;
    private String nombreUsuario;
    Scanner sc;

    public ClienteUDP(int puertoServidor, String ipServidor) throws SocketException, UnknownHostException {
        sc = new Scanner(System.in);
        this.nombreUsuario = nombreUsuario;
        this.ipServidor = InetAddress.getByName(ipServidor);
        this.puertoServidor = puertoServidor;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

        sendingData = getFirstRequest();
        while (mustContinue(sendingData)) {
            DatagramPacket packet = new DatagramPacket(sendingData,sendingData.length,ipServidor,puertoServidor);
            socket.send(packet);
            packet = new DatagramPacket(receivedData,1024);
            socket.receive(packet);
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }

    }

    private byte[] getFirstRequest() {
        System.out.print("Entra el teu nom: ");
        nombreUsuario = sc.nextLine();
        return nombreUsuario.getBytes();
    }
    private byte[] getDataToRequest(byte[] data, int length) {
        String rebut = new String(data,0, length);
        //Imprimeix el nom del client + el que es rep del server i demana més dades
        System.out.print(nombreUsuario+"("+rebut+")"+"> ");
        String msg = sc.nextLine();
        return msg.getBytes();
    }
    private boolean mustContinue(byte [] data) {
        String msg = new String(data).toLowerCase();
        return !msg.equals("adeu");
    }

    public static void main(String[] args) {
        try {
            ClienteUDP client = new ClienteUDP(12345,"127.0.0.1");
            client.runClient();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }


}