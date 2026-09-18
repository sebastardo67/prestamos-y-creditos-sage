package main.java.org.sage.prestamos.creditos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import main.java.org.sage.prestamos.creditos.config.ConnectionDb;
import main.java.org.sage.prestamos.creditos.model.Usuarios;

public class UsuariosRepository {

    /**
     * Busca un usuario por correo electrónico.
     */
    public Optional<Usuarios> buscarPorEmail(String email) {

        String sql = """
                SELECT
                    id_usuario,
                    nombre,
                    apellido,
                    username,
                    email,
                    password_hash,
                    id_rol,
                    activo,
                    fecha_creacion,
                    fecha_actualizacion
                FROM usuarios
                WHERE email = ?
                """;

        try (
            Connection connection =
                    ConnectionDb.getconnectionDataBase();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    Usuarios usuario = new Usuarios();

                    usuario.setIdUsuario(
                            rs.getInt("id_usuario")
                    );

                    usuario.setNombre(
                            rs.getString("nombre")
                    );

                    usuario.setApellido(
                            rs.getString("apellido")
                    );

                    usuario.setUsername(
                            rs.getString("username")
                    );

                    usuario.setEmail(
                            rs.getString("email")
                    );

                    usuario.setPasswordHash(
                            rs.getString("password_hash")
                    );

                    usuario.setIdRol(
                            rs.getInt("id_rol")
                    );

                    usuario.setActivo(
                            rs.getBoolean("activo")
                    );

                    Timestamp fechaCreacion =
                            rs.getTimestamp("fecha_creacion");

                    if (fechaCreacion != null) {
                        usuario.setFechaCreacion(
                                fechaCreacion.toLocalDateTime()
                        );
                    }

                    Timestamp fechaActualizacion =
                            rs.getTimestamp("fecha_actualizacion");

                    if (fechaActualizacion != null) {
                        usuario.setFechaActualizacion(
                                fechaActualizacion.toLocalDateTime()
                        );
                    }

                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return Optional.empty();
    }


    /**
     * Busca un usuario por username.
     */
    public Optional<Usuarios> buscarPorUsername(String username) {

        String sql = """
                SELECT
                    id_usuario,
                    nombre,
                    apellido,
                    username,
                    email,
                    password_hash,
                    id_rol,
                    activo,
                    fecha_creacion,
                    fecha_actualizacion
                FROM usuarios
                WHERE username = ?
                """;

        try (
            Connection connection =
                    ConnectionDb.getconnectionDataBase();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    Usuarios usuario = new Usuarios();

                    usuario.setIdUsuario(
                            rs.getInt("id_usuario")
                    );

                    usuario.setNombre(
                            rs.getString("nombre")
                    );

                    usuario.setApellido(
                            rs.getString("apellido")
                    );

                    usuario.setUsername(
                            rs.getString("username")
                    );

                    usuario.setEmail(
                            rs.getString("email")
                    );

                    usuario.setPasswordHash(
                            rs.getString("password_hash")
                    );

                    usuario.setIdRol(
                            rs.getInt("id_rol")
                    );

                    usuario.setActivo(
                            rs.getBoolean("activo")
                    );

                    Timestamp fechaCreacion =
                            rs.getTimestamp("fecha_creacion");

                    if (fechaCreacion != null) {
                        usuario.setFechaCreacion(
                                fechaCreacion.toLocalDateTime()
                        );
                    }

                    Timestamp fechaActualizacion =
                            rs.getTimestamp("fecha_actualizacion");

                    if (fechaActualizacion != null) {
                        usuario.setFechaActualizacion(
                                fechaActualizacion.toLocalDateTime()
                        );
                    }

                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return Optional.empty();
    }


    /**
     * Guarda un nuevo usuario en la base de datos.
     */
    public boolean guardar(Usuarios usuario) {

        String sql = """
                INSERT INTO usuarios (
                    nombre,
                    apellido,
                    username,
                    email,
                    password_hash,
                    id_rol,
                    activo,
                    fecha_creacion,
                    fecha_actualizacion
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection =
                    ConnectionDb.getconnectionDataBase();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    usuario.getNombre()
            );

            statement.setString(
                    2,
                    usuario.getApellido()
            );

            statement.setString(
                    3,
                    usuario.getUsername()
            );

            statement.setString(
                    4,
                    usuario.getEmail()
            );

            statement.setString(
                    5,
                    usuario.getPasswordHash()
            );

            statement.setInt(
                    6,
                    usuario.getIdRol()
            );

            statement.setBoolean(
                    7,
                    usuario.isActivo()
            );

            statement.setTimestamp(
                    8,
                    Timestamp.valueOf(
                            usuario.getFechaCreacion()
                    )
            );

            statement.setTimestamp(
                    9,
                    Timestamp.valueOf(
                            usuario.getFechaActualizacion()
                    )
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}
