import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        try {
            ServerSocket socketServidor = new ServerSocket(1234);
            System.out.println("Esperando Conexiones...");

            while (true) {
                //obtener la dirección del socket del cliente
                Socket socketCliente = socketServidor.accept();
                //crear una instancia de la clase extendida 
                HiloCliente hilo = new HiloCliente(socketCliente);
                //llamar el método start para iniciar la ejecución del hilo
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
