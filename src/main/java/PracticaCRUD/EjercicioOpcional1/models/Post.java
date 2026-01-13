package PracticaCRUD.EjercicioOpcional1.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    public int id;
    public String titulo;
    public String contenido;
    public String autor;
    public String fechaPublicacion;
    public List<Comentario> comentarios; // Lista anidada

    public Post() {} 
    public Post(int id, String titulo, String contenido, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.autor = autor;
        this.fechaPublicacion = LocalDateTime.now().toString();
        this.comentarios = new ArrayList<>();
    }
}