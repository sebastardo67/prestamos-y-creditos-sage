package main.java.org.sage.prestamos.creditos.dto.response;

public class UsuarioAdminResponse {

    private final int idUsuario;
    private final String nombreCompleto;
    private final String username;
    private final String email;
    private final boolean activo;

    public UsuarioAdminResponse(
            int idUsuario,
            String nombreCompleto,
            String username,
            String email,
            boolean activo
    ) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.email = email;
        this.activo = activo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivo() {
        return activo;
    }
}