import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Cliente conectado");

            Socket socketCliente = new Socket("localhost", 1234);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            String datosRecibidos;
            while ((datosRecibidos = entrada.readLine()) != null) {
                System.out.println("Servidor: " + datosRecibidos);

                if (datosRecibidos.equals("No hay más preguntas.")) {
                    break;
                } else if (datosRecibidos.equals("Siguiente pregunta:")) {
                    continue;
                } else if (datosRecibidos.equals("Incorrecto.") || datosRecibidos.equals("Correcto!")) {
                    continue;
                } else {
                    System.out.print("Escriba su respuesta: ");
                    String respuesta = sc.nextLine();
                    salida.println(respuesta);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
