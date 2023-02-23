package udp_2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ClienteJuego {
    private int portDesti;
    private int result;
    private String Nom, ipSrv;
    private int intents;
    private InetAddress adrecaDesti;

    public ClienteJuego(String ip, int port) {
        this.portDesti = port;
        result = -1;
        intents = 0;
        ipSrv = ip;

        try {
            adrecaDesti = InetAddress.getByName(ipSrv);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setNom(String n) {
        Nom=n;
    }

    public int getIntents () {
        return intents;
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[4];
        int n;

        //Missatge de benvinguda
        System.out.println("Hola " + Nom + "!\n Prueba un número: ");

        //Bucle de joc
        while(result!=0 & result!=-2) {

            Scanner sc = new Scanner(System.in);
            n = sc.nextInt();
            byte[] missatge = ByteBuffer.allocate(4).putInt(n).array();

            //creació del paquet a enviar
            DatagramPacket packet = new DatagramPacket(missatge,missatge.length,adrecaDesti,portDesti);
            //creació d'un sòcol temporal amb el qual realitzar l'enviament
            DatagramSocket socket = new DatagramSocket();
            //Enviament del missatge
            socket.send(packet);

            //creació del paquet per rebre les dades
            packet = new DatagramPacket(receivedData, 4);
            //espera de les dades
            socket.setSoTimeout(5000);
            try {
                socket.receive(packet);
                //processament de les dades rebudes i obtenció de la resposta
                result = getDataToRequest(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e) {
                System.out.println("El servidor no respòn: " + e.getMessage());
                result=-2;
            }
        }


    }

    private int getDataToRequest(byte[] data, int length) {
        int nombre = ByteBuffer.wrap(data).getInt();

        if(nombre==0) System.out.println("Correcto!");
        else if (nombre==1) System.out.println("Más pequeño!");
        else System.out.println("Más grande!");
        intents++;

        return nombre;
    }



    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static void main(String[] args) {
        String jugador, ipSrv, port;

        System.out.print("IP del servidor: ");
        Scanner sip = new Scanner(System.in);
        ipSrv = sip.next();
        System.out.print("Puerto del servidor: ");
        port = sip.next();
        System.out.print("Nombre del jugador: ");
        jugador = sip.next();

        ClienteJuego cAdivina = new ClienteJuego(ipSrv, Integer.valueOf(port));

        cAdivina.setNom(jugador);
        try {
            cAdivina.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(cAdivina.getResult() == 0) {
            System.out.println("¡Lo has adivinado en "+ cAdivina.getIntents() +" intentos!");
        } else {
            System.out.println("Has perdido! :(");
        }

    }

}