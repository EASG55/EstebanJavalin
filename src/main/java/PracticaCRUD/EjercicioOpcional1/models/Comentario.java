package PracticaCRUD.EjercicioOpcional1.models;

import java.time.LocalDateTime;

public class Comentario {
    public String autor;
    public String contenido;
    public String fecha;

    public Comentario() {} // Constructor vacío para Jackson
    public Comentario(String autor, String contenido) {
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now().toString();
    }
}

