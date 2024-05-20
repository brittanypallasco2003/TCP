import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

//1. Crear una clase que se extienda de la clase Thread
public class HiloCliente extends Thread {
    private DatagramSocket socket;
    private DatagramPacket paqueteEntrada;
    private static int indicePregunta = 0;

    public HiloCliente(DatagramSocket socket, DatagramPacket paqueteEntrada) {
        this.socket = socket;
        this.paqueteEntrada = paqueteEntrada;
    }

    // 2. Sobrescribir el método run con toda la lógica que requerirá el hilo que le
    // asignará el servidor a cada cliente
    public void run() {

        String mensajeAlCliente;
        byte[] bufferSalida;

        try {
            Scanner escaner = new Scanner(System.in);
            // extraer la información del paquete
            String mensajeRecibido = new String(paqueteEntrada.getData());
            System.out.println("El mensaje del cliente es: " + mensajeRecibido);

            // obtener la dirección del cliente
            InetAddress direccionClienteIp = paqueteEntrada.getAddress();
            int puertoCliente = paqueteEntrada.getPort();

            while (true) {
                if (indicePregunta < Servidor.getPreguntas().size()) {
                    Pregunta preguntaActual = Servidor.getPreguntas().get(indicePregunta);
                   String mensajeSalida = preguntaActual.getPregunta();
                   bufferSalida=mensajeSalida.getBytes();
                   DatagramPacket paqueteSalida1 = new DatagramPacket(bufferSalida, bufferSalida.length,
                                direccionClienteIp, puertoCliente);
                        socket.send(paqueteSalida1);

                    String respuestaRecibida = new String(paqueteEntrada.getData());
                    System.out.println("Respuesta: " + respuestaRecibida);

                    if (preguntaActual.esRespuestaCorrecta(respuestaRecibida)) {
                        mensajeAlCliente = "Correcto";
                        bufferSalida = mensajeAlCliente.getBytes();
                        DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length,
                                direccionClienteIp, puertoCliente);
                        socket.send(paqueteSalida);
                    } else {
                        mensajeAlCliente = "Incorrecto. La respuesta correcta es: "
                                + preguntaActual.getRespuestaCorrecta();
                        bufferSalida = mensajeAlCliente.getBytes();
                        DatagramPacket paqueteSalida2 = new DatagramPacket(bufferSalida, bufferSalida.length,
                                direccionClienteIp, puertoCliente);
                        socket.send(paqueteSalida2);
                    }

                    indicePregunta++;
                    if (indicePregunta < Servidor.getPreguntas().size()) {
                        mensajeAlCliente = "Siguiente pregunta:";
                        System.out.println(mensajeAlCliente);
                        bufferSalida=mensajeAlCliente.getBytes();
                        DatagramPacket paqueteSalida2 = new DatagramPacket(bufferSalida, bufferSalida.length,
                                direccionClienteIp, puertoCliente);
                        socket.send(paqueteSalida2);

                    } else {
                        mensajeAlCliente = "No hay más preguntas.";
                        System.out.println(mensajeAlCliente);
                        DatagramPacket paqueteSalida2 = new DatagramPacket(bufferSalida, bufferSalida.length,
                                direccionClienteIp, puertoCliente);
                        socket.send(paqueteSalida2);
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
