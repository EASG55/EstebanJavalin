package PracticaCRUD.EjercicioObligatorio3.models;

import java.time.LocalDateTime;

public class Usuario {
    private String username;
    private String password;
    private String email;
    private String token;
    private String fechaRegistro = LocalDateTime.now().toString();

    // El constructor vacío es el que usa Javalin/Jackson
    public Usuario() {
        // Al inicializar arriba, ya no necesitas poner nada aquí
    }

    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        // this.fechaRegistro ya tiene valor por la inicialización de arriba
    }

    // Getters y Setters...
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}