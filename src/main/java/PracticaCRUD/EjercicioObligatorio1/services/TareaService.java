package PracticaCRUD.EjercicioObligatorio1.services;

import PracticaCRUD.EjercicioObligatorio1.models.Tarea;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TareaService {
    private static final Map<Integer, Tarea> tareas = new HashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas.values());
    }

    public Tarea obtenerPorId(int id) {
        return tareas.get(id);
    }

    public Tarea crear(Tarea tarea) {
        int id = idGenerator.getAndIncrement();
        tarea.setId(id);
        // Aseguramos fecha si no viene
        if (tarea.getFechaCreacion() == null) {
            tarea.setFechaCreacion(LocalDateTime.now().toString());
        }
        tareas.put(id, tarea);
        return tarea;
    }

    public Tarea actualizar(int id, Tarea tareaActualizada) {
        if (!tareas.containsKey(id)) return null;
        Tarea tarea = tareas.get(id);
        tarea.setTitulo(tareaActualizada.getTitulo());
        tarea.setDescripcion(tareaActualizada.getDescripcion());
        return tarea;
    }

    public boolean eliminar(int id) {
        return tareas.remove(id) != null;
    }

    public Tarea marcarCompletada(int id) {
        Tarea tarea = tareas.get(id);
        if (tarea != null) {
            tarea.setCompletada(true);
        }
        return tarea;
    }
}