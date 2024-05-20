import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static List<Pregunta> preguntas;

    static {
        preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "Paris"));
        preguntas.add(new Pregunta("¿Cuántos continentes hay en el mundo?", "7"));
        preguntas.add(new Pregunta("¿Quién pintó la Mona Lisa?", "Leonardo da Vinci"));
        preguntas.add(new Pregunta("¿Cuál es el planeta más grande del sistema solar?", "Jupiter"));
        preguntas.add(new Pregunta("¿Qué elemento tiene el símbolo 'O' en la tabla periódica?", "Oxigeno"));
    }

    public static void main(String[] args) {
        int puerto = 3000;
        try {
            DatagramSocket socket = new DatagramSocket(puerto);// indicar número de puerto
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                // crear arreglo de bytes para recibir los datos
                byte[] bufferEntrada = new byte[1024];
                // crear datagrama para recibir los datos
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                // recibir el paquete o el datagrama
                socket.receive(paqueteEntrada);

                // iniciar un hilo para cada cliente
                Thread hiloCliente = new HiloCliente(socket, paqueteEntrada);
                hiloCliente.start();
  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
