import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//1. Crear una clase que se extienda de la clase Thread
public class HiloCliente extends Thread {
    private Socket socketCliente;
    private static int indicePregunta = 0;

    public HiloCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }
//2. Sobrescribir el método run con toda la lógica que requerirá el hilo que le asignará el servidor a cada cliente
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            while (true) {
                if (indicePregunta < Servidor.getPreguntas().size()) {
                    Pregunta preguntaActual = Servidor.getPreguntas().get(indicePregunta);
                    salida.println(preguntaActual.getPregunta());

                    String respuestaCliente = entrada.readLine();
                    System.out.println("Respuesta: " + respuestaCliente);
                    if (preguntaActual.esRespuestaCorrecta(respuestaCliente)) {
                        salida.println("Correcto!");
                    } else {
                        salida.println("Incorrecto. La respuesta correcta es: " + preguntaActual.getRespuestaCorrecta());
                    }

                    indicePregunta++;
                    if (indicePregunta < Servidor.getPreguntas().size()) {
                        salida.println("Siguiente pregunta:");
                    } else {
                        salida.println("No hay más preguntas.");
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
