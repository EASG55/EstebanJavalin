//importación de la libreria de Javalin
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

/*
 * Ejemplo 4: Respuestas JSON con Javalin
 * Ejemplo de serializacion automatica de objetos java a JSON.
 * Javalin utiliza Jackson para convertir el objeto Map en JSON sin
 */

public class Ejemplo4JsonJavalin {
    public static void main(String[] args) {
        //Creacion del endpoint con Javalin en el puerto 7070
        Javalin app = Javalin.create().start(7070);

        //Muestra un usuario con Map a traves del metodo GET
        app.get("/usuario", ctx -> {
            Map<String, Object> usuario = Map.of(
                    "id", 1,
                    "nombre", "Esteban Soto",
                    "Edad", 23
            );
            //Conversion del objeto de java a JSON
            ctx.json(usuario);
        });

        System.out.println("Servidor iniciado en http://localhost:7070");
        System.out.println("Prueba: http://localhost:7070/usuario");
    }
}
