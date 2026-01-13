package PracticaCRUD.EjercicioOpcional1;

import PracticaCRUD.EjercicioOpcional1.models.Comentario;
import PracticaCRUD.EjercicioOpcional1.models.Post;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aplicación de Blog API REST.
 * <p>
 * Gestiona la creación de posts y la adición de comentarios anidados.
 * Demuestra relaciones 1:N en memoria.
 * </p>
 */

public class BlogApp {

    /** Almacenamiento en memoria de los posts (Simulación de DB). */
    private static Map<Integer, Post> posts = new HashMap<>();

    /** Generador de IDs secuenciales y thread-safe. */
    private static AtomicInteger idGen = new AtomicInteger(1);

    /**
     * Punto de entrada de la aplicación.
     * Define las rutas y la configuración del servidor Javalin.
     *
     * @param args argumentos de línea de comando (no utilizados).
     */
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        /**
         * Endpoint: Crear un nuevo Post.
         * Método: POST /posts
         * * Recibe un JSON con título, contenido y autor.
         * Asigna ID y fecha automáticamente.
         */
        app.post("/posts", ctx -> {
            // Deserialización automática del cuerpo de la petición
            Post nuevo = ctx.bodyAsClass(Post.class);

            // Lógica de negocio: asignar metadatos
            nuevo.id = idGen.getAndIncrement();
            nuevo.fechaPublicacion = java.time.LocalDateTime.now().toString();
            nuevo.comentarios = new java.util.ArrayList<>();

            // Guardar en "Base de Datos"
            posts.put(nuevo.id, nuevo);

            // Respuesta 201 Created
            ctx.status(201).json(nuevo);
        });

        /**
         * Endpoint: Añadir comentario a un Post existente.
         * Método: POST /posts/{id}/comentarios
         * * @param id El ID del post al que se quiere comentar (Path Param).
         */
        app.post("/posts/{id}/comentarios", ctx -> {
            int postId = Integer.parseInt(ctx.pathParam("id"));
            Post post = posts.get(postId);

            if (post != null) {
                Comentario comentario = ctx.bodyAsClass(Comentario.class);
                comentario.fecha = java.time.LocalDateTime.now().toString();

                // Relación: Añadir a la lista del objeto padre
                post.comentarios.add(comentario);

                ctx.status(201).json(post);
            } else {
                ctx.status(404).json(Map.of("error", "Post no encontrado"));
            }
        });

        /**
         * Endpoint: Obtener un post por ID.
         * Método: GET /posts/{id}
         * Retorna el post completo incluyendo sus comentarios anidados.
         */
        app.get("/posts/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Post post = posts.get(id);
            if(post != null) {
                ctx.json(post);
            } else {
                ctx.status(404).json(Map.of("error", "No existe"));
            }
        });
    }
}