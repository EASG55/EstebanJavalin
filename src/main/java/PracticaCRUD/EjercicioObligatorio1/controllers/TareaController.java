package PracticaCRUD.EjercicioObligatorio1.controllers;

import PracticaCRUD.EjercicioObligatorio1.models.Tarea;
import PracticaCRUD.EjercicioObligatorio1.services.TareaService;
import io.javalin.http.Context;
import java.util.Map;

public class TareaController {
    private static final TareaService service = new TareaService();

    /*
     * Obtiene todas las tareas
     * @param ctx contexto de Javalin con request/response
     */
    public static void obtenerTodas(Context ctx) {
        ctx.json(service.obtenerTodas());
    }

    /*
     * Obtiene una tarea por ID
     * @param ctx contexto de Javalin con parámetro {id}
     */
    public static void obtenerPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Tarea tarea = service.obtenerPorId(id);
        if (tarea != null) {
            ctx.json(tarea);
        } else {
            ctx.status(404).json(Map.of("error", "Tarea no encontrada"));
        }
    }

    /*
     * Crea una nueva tarea
     * @param ctx contexto de Javalin con body JSON
     */
    public static void crear(Context ctx) {
        Tarea tarea = ctx.bodyAsClass(Tarea.class);
        if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
            ctx.status(400).json(Map.of("error", "El título es obligatorio"));
            return;
        }
        Tarea creada = service.crear(tarea);
        ctx.status(201).json(creada);
    }

    /*
     * Actualiza una tarea existente
     * @param ctx contexto de Javalin con parámetro {id} y body JSON
     */
    public static void actualizar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Tarea tarea = ctx.bodyAsClass(Tarea.class);
        Tarea actualizada = service.actualizar(id, tarea);
        if (actualizada != null) {
            ctx.json(actualizada);
        } else {
            ctx.status(404).json(Map.of("error", "Tarea no encontrada"));
        }
    }

    /*
     * Elimina una tarea
     * @param ctx contexto de Javalin con parámetro {id}
     */
    public static void eliminar(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        if (service.eliminar(id)) {
            ctx.status(204); // No Content
        } else {
            ctx.status(404).json(Map.of("error", "Tarea no encontrada"));
        }
    }

    /*
     * Marca una tarea como completada
     * @param ctx contexto de Javalin con parámetro {id}
     */
    public static void marcarCompletada(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Tarea tarea = service.marcarCompletada(id);
        if (tarea != null) {
            ctx.json(tarea);
        } else {
            ctx.status(404).json(Map.of("error", "Tarea no encontrada"));
        }
    }
}