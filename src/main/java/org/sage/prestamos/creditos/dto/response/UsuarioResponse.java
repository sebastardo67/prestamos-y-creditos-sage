package main.java.org.sage.prestamos.creditos.dto.response;

public class UsuarioResponse {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private int idRol;
    private boolean activo;

    public UsuarioResponse(int idUsuario, String nombre, String apellido,
            String username, String email, int idRol, boolean activo) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.idRol = idRol;
        this.activo = activo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}