import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        int puerto = 3000;
        try {
            DatagramSocket socket = new DatagramSocket();// indicar número de puerto

            // conocer la dirección ip del servidor
            InetAddress direccionIpServidor = InetAddress.getByName("localhost");

            Scanner sc = new Scanner(System.in);
            String mensajeSalida;
            mensajeSalida=sc.nextLine();

            // crear arreglo de bytes para empaquetar la respuesta
            byte[] bufferSalida = mensajeSalida.getBytes();
            // crear datagrama para mandar los datos junto a la ip y al puerto del cliene
            DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionIpServidor,
                    puerto);
            socket.send(paqueteSalida);

            // RECIBIR INFORMACIÓN DEL SERVIDOR
            // crear arreglo de bytes para recibir el mensaje del servidor
            byte[] bufferEntrada = new byte[1024];
            // crear datagrama para recibir los datos
            DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
            // recibir el paquete o el datagrama
            socket.receive(paqueteEntrada);

            String datosRecibidos;
            String mensajeRecibido = new String(paqueteEntrada.getData());
            datosRecibidos = mensajeRecibido;

            while (datosRecibidos != null) {
                System.out.println("Servidor: " + datosRecibidos);

                if (datosRecibidos.equals("No hay más preguntas.")) {
                    break;
                } else if (datosRecibidos.equals("Siguiente pregunta:")) {
                    continue;
                } else if (datosRecibidos.equals("Incorrecto.") || datosRecibidos.equals("Correcto!")) {
                    continue;
                } else {
                    System.out.print("Escriba su respuesta: ");
                    mensajeSalida = sc.nextLine();
                    bufferSalida = mensajeSalida.getBytes();
                    // crear datagrama para mandar los datos junto a la ip y al puerto del cliene
                    paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length,
                            direccionIpServidor,
                            puerto);
                    socket.send(paqueteSalida);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
