package main.java.org.sage.prestamos.creditos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import main.java.org.sage.prestamos.creditos.config.ConnectionDb;
import main.java.org.sage.prestamos.creditos.dto.request.UsuarioRequest;
import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.security.jdbcrypt.BCrypt;

public class UsuariosRepository {

    // Reemplaza esta conexión por tu clase de gestión de BD (DatabaseConnection.getConnection(), etc.)
    private Connection getConnection() throws SQLException {
        // return ConexionBD.obtenerConexion();
        return null; 
    }

    public Optional<Usuarios> buscarPorEmail(String email) {
        String sql = "SELECT id_usuario, nombre, apellido, username, email, password_hash, " +
                     "id_rol, activo, fecha_creacion, fecha_actualizacion " +
                     "FROM usuarios WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuarios usuario = new Usuarios();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPasswordHash(rs.getString("password_hash"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    usuario.setActivo(rs.getBoolean("activo"));

                    Timestamp fc = rs.getTimestamp("fecha_creacion");
                    if (fc != null) usuario.setFechaCreacion(fc.toLocalDateTime());

                    Timestamp fa = rs.getTimestamp("fecha_actualizacion");
                    if (fa != null) usuario.setFechaActualizacion(fa.toLocalDateTime());

                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    public boolean guardarUsuario(UsuarioRequest request) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, contrasena_hash, id_rol) VALUES (?, ?, ?, ?, ?)";
        
        // Encriptar la contraseña antes de guardar
        String hashPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        try (Connection conn = ConnectionDb.getconnectionDataBase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, request.getNombre());
            ps.setString(2, request.getApellido());
            ps.setString(3, request.getEmail());
            ps.setString(4, hashPassword);
            ps.setInt(5, request.getIdRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}