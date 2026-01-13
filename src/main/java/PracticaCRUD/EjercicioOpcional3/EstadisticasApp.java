package PracticaCRUD.EjercicioOpcional3;

import PracticaCRUD.EjercicioOpcional3.models.Venta;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
/**
 * API analítica para registro de ventas y cálculo de estadísticas.
 * Realiza agregaciones en tiempo real (Suma, Promedio, Moda).
 */
public class EstadisticasApp {

    private static List<Venta> ventas = new ArrayList<>();
    private static AtomicInteger idGen = new AtomicInteger(0);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.post("/ventas", EstadisticasApp::registrarVenta);
        app.get("/estadisticas", EstadisticasApp::calcularEstadisticas);
    }

    /**
     * Registra una transacción de venta.
     * * @param ctx Contexto con body JSON de la venta (producto, cantidad, precioUnitario).
     */
    public static void registrarVenta(Context ctx) {
        Venta v = ctx.bodyAsClass(Venta.class);

        // Creamos una nueva instancia para asignar ID y Fecha del servidor
        Venta nueva = new Venta(
                idGen.incrementAndGet(),
                v.getProducto(),
                v.getCantidad(),
                v.getPrecioUnitario()
        );

        ventas.add(nueva);
        ctx.status(201).json(nueva);
    }

    /**
     * Calcula métricas estadísticas sobre las ventas registradas.
     * Soporta filtrado opcional por rango de fechas mediante query params.
     * * <p>Métricas calculadas:
     * <ul>
     * <li>Total de ingresos (Suma)</li>
     * <li>Número de transacciones (Conteo)</li>
     * <li>Producto más vendido (Moda)</li>
     * <li>Ticket promedio</li>
     * </ul>
     * </p>
     * * @param ctx Contexto de Javalin.
     * Query Params opcionales: "fecha_inicio" y "fecha_fin" (formato YYYY-MM-DD).
     */
    public static void calcularEstadisticas(Context ctx) {
        String inicioStr = ctx.queryParam("fecha_inicio");
        String finStr = ctx.queryParam("fecha_fin");

        List<Venta> filtradas = ventas;

        // Filtrado por fechas si los parámetros existen
        if (inicioStr != null && finStr != null) {
            try {
                LocalDate inicio = LocalDate.parse(inicioStr);
                LocalDate fin = LocalDate.parse(finStr);

                filtradas = ventas.stream()
                        //Parseamos v.getFecha() (que ahora es String) a LocalDate para comparar
                        .filter(v -> {
                            LocalDate fechaVenta = LocalDate.parse(v.getFecha());
                            return !fechaVenta.isBefore(inicio) && !fechaVenta.isAfter(fin);
                        })
                        .collect(Collectors.toList());
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "Formato de fecha inválido"));
                return;
            }
        }

        if (filtradas.isEmpty()) {
            ctx.json(Map.of("mensaje", "No hay datos para el rango seleccionado"));
            return;
        }

        // 1. Cálculo de Total Vendido
        double totalVentas = filtradas.stream().mapToDouble(Venta::getTotal).sum();

        // 2. Conteo de transacciones
        long numTransacciones = filtradas.size();

        // 3. Cálculo de Promedio
        double promedio = totalVentas / numTransacciones;

        // 4. Cálculo del Producto más vendido (Moda)
        // Agrupa por nombre de producto y suma las cantidades
        String masVendido = filtradas.stream()
                .collect(Collectors.groupingBy(Venta::getProducto, Collectors.summingInt(Venta::getCantidad)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Busca el máximo valor del mapa
                .map(Map.Entry::getKey)            // Obtiene el nombre del producto
                .orElse("N/A");

        // Construcción de la respuesta JSON
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalVentas", totalVentas);
        stats.put("numeroTransacciones", numTransacciones);
        stats.put("productoMasVendido", masVendido);
        stats.put("ventaPromedio", Math.round(promedio * 100.0) / 100.0); // Redondeo a 2 decimales

        ctx.json(stats);
    }
}