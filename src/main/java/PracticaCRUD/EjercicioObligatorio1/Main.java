package PracticaCRUD.EjercicioObligatorio1;

import PracticaCRUD.EjercicioObligatorio1.controllers.TareaController;
import io.javalin.Javalin;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        // Endpoints
        app.get("/tareas", TareaController::obtenerTodas);
        app.get("/tareas/{id}", TareaController::obtenerPorId);
        app.post("/tareas", TareaController::crear);
        app.put("/tareas/{id}", TareaController::actualizar);
        app.delete("/tareas/{id}", TareaController::eliminar);
        app.patch("/tareas/{id}/completar", TareaController::marcarCompletada);

        // Manejo de Excepciones Global
        app.exception(NumberFormatException.class, (e, ctx) -> {
            ctx.status(400).json(Map.of("error", "ID debe ser numérico"));
        });
    }
}