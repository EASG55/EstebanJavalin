package PracticaCRUD.EjercicioObligatorio3.services;

import PracticaCRUD.EjercicioObligatorio3.models.Usuario;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio de autenticación.
 */
public class AuthService {

    // Simulación de base de datos en memoria
    private static final Map<String, Usuario> usuariosDB = new HashMap<>();

    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario Objeto usuario con los datos del registro.
     * @throws IllegalArgumentException si el usuario ya existe.
     */
    public Usuario registrar(Usuario usuario) {
        if (usuariosDB.containsKey(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        usuariosDB.put(usuario.getUsername(), usuario);
        return usuario;
    }

    /**
     * Realiza el login y genera un token simple.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return El token generado si las credenciales son correctas.
     * @throws IllegalArgumentException si las credenciales son inválidas.
     */
    public String login(String username, String password) {
        Usuario usuario = usuariosDB.get(username);

        if (usuario == null || !usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // Generar token simple: username_timestamp
        String token = username + "_" + System.currentTimeMillis();
        usuario.setToken(token); // Guardamos el token en el usuario

        return token;
    }

    /**
     * Valida si un token es correcto y pertenece a un usuario.
     * @param token Token a validar.
     * @return username del usuario o null si es inválido.
     */
    public String validarToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        // Buscamos si algún usuario tiene este token activo
        for (Usuario u : usuariosDB.values()) {
            if (token.equals(u.getToken())) {
                return u.getUsername();
            }
        }
        return null;
    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     */
    public Usuario obtenerUsuario(String username) {
        return usuariosDB.get(username);
    }
}