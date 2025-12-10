//importación de la libreria de Javalin
import io.javalin.Javalin;

//GET (Select), POST(Insert), PUT(Update), DELETE(Delete)

/*
*API REST BASICA EN JAVA CON JAVALIN
*Aplicacion simple que proporciona un endpoint tipo GET para obtener un saludo
*El servidor se inicia en el puerto 7070 con un mensaje de texto plano
*/


// http://localhost:7070/
public class Ejemplo1JavalinBasico {
    public static void main(String[] args) {
        //Creacion del endpoint con Javalin en el puerto 7070
        Javalin app = Javalin.create().start(7070);

        //Creacion del saludo con texto plano con el metodo GET
        app.get("/", ctx -> {
            ctx.result("Hola desde Javalin!");
        });

        System.out.println("Servidor iniciado en http://localhost:7070");
    }
}
