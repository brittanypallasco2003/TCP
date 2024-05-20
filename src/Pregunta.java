public class Pregunta {
    private String pregunta;
    private String respuestaCorrecta;

    public Pregunta(String pregunta, String respuestaCorrecta) {
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public boolean esRespuestaCorrecta(String respuesta) {
        return respuesta.equalsIgnoreCase(respuestaCorrecta);
    }
}
