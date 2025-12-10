package PracticaCRUD.EjercicioObligatorio3;

import PracticaCRUD.EjercicioObligatorio3.controllers.AuthController;
import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        // Esto captura el "throw new UnauthorizedResponse" y devuelve el JSON 401
        app.exception(UnauthorizedResponse.class, (e, ctx) -> {
            ctx.status(401).json(Map.of("error", "No autorizado. Token requerido o inválido"));
        });

        // También es buena práctica capturar errores inesperados (500)
        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace(); // Para ver el error en consola
            ctx.status(500).json(Map.of("error", "Error interno del servidor"));
        });

        // Rutas Públicas
        app.post("/auth/registrar", AuthController::registrar);
        app.post("/auth/login", AuthController::login);

        // Seguridad
        app.before("/perfil", AuthController::verificarAutenticacion);

        // Rutas Protegidas
        app.get("/perfil", AuthController::obtenerPerfil);

        System.out.println("Servidor iniciado en http://localhost:7070");
    }
}