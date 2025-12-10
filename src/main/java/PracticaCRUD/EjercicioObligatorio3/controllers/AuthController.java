package PracticaCRUD.EjercicioObligatorio3.controllers;

import PracticaCRUD.EjercicioObligatorio3.models.Usuario;
import PracticaCRUD.EjercicioObligatorio3.services.AuthService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador que maneja las peticiones HTTP relacionadas con autenticación.
 */
public class AuthController {

    private static final AuthService authService = new AuthService();

    /**
     * Endpoint: POST /auth/registrar
     * Registra un nuevo usuario.
     */
    public static void registrar(Context ctx) {
        try {
            Usuario nuevoUsuario = ctx.bodyAsClass(Usuario.class);

            // Validaciones básicas
            if (nuevoUsuario.getUsername() == null || nuevoUsuario.getPassword() == null) {
                ctx.status(400).json(Map.of("error", "Faltan datos obligatorios"));
                return;
            }
            authService.registrar(nuevoUsuario);

            ctx.status(201).json(Map.of(
                "mensaje", "Usuario registrado exitosamente",
                "username", nuevoUsuario.getUsername()
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint: POST /auth/login
     * Realiza login y devuelve un token.
     */
    public static void login(Context ctx) {
        try {
            Usuario credentials = ctx.bodyAsClass(Usuario.class);
            String token = authService.login(credentials.getUsername(), credentials.getPassword());

            ctx.status(200).json(Map.of(
                "token", token,
                "username", credentials.getUsername()
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(401).json(Map.of("error", "Usuario o contraseña incorrectos"));
        }
    }

    /**
     * Endpoint: GET /perfil
     * Obtiene el perfil del usuario autenticado (Ruta protegida).
     */
    public static void obtenerPerfil(Context ctx) {
        String username = ctx.attribute("username");
        Usuario usuario = authService.obtenerUsuario(username);

        if (usuario != null) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("username", usuario.getUsername());
            respuesta.put("email", usuario.getEmail());
            respuesta.put("fechaRegistro", usuario.getFechaRegistro());

            ctx.json(respuesta);
        } else {
            ctx.status(404).json(Map.of("error", "Usuario no encontrado"));
        }
    }

    /**
     * Middleware de seguridad.
     * Verifica que la petición tenga un token válido en el header Authorization.
     */
    public static void verificarAutenticacion(Context ctx) {
        String token = ctx.header("Authorization");
        String username = authService.validarToken(token);

        if (username == null) {
            ctx.status(401).json(Map.of("error", "Token inválido"));
            // Esta línea detiene la ejecución para que no llegue al controller
            throw new io.javalin.http.UnauthorizedResponse("No autorizado");
        }

        ctx.attribute("username", username);
    }
}