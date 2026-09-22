package main.java.org.sage.prestamos.creditos.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.org.sage.prestamos.creditos.config.ConnectionDb;
import main.java.org.sage.prestamos.creditos.model.Usuarios;

public class UsuariosRepository {

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
                LIMIT 1
                """;

        try (
                Connection connection = ConnectionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "No se pudo consultar el usuario en la base de datos.",
                    e
            );
        }

        return Optional.empty();
    }

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
                LIMIT 1
                """;

        try (
                Connection connection = ConnectionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "No se pudo consultar el nombre de usuario.",
                    e
            );
        }

        return Optional.empty();
    }

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
                Connection connection = ConnectionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getUsername());
            statement.setString(4, usuario.getEmail());
            statement.setString(5, usuario.getPasswordHash());
            statement.setInt(6, usuario.getIdRol());
            statement.setBoolean(7, usuario.isActivo());

            statement.setTimestamp(
                    8,
                    Timestamp.valueOf(usuario.getFechaCreacion())
            );

            statement.setTimestamp(
                    9,
                    Timestamp.valueOf(usuario.getFechaActualizacion())
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "No se pudo guardar el usuario en la base de datos.",
                    e
            );
        }
    }

    private Usuarios mapearUsuario(ResultSet rs) throws SQLException {

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

        return usuario;
    }
    public Optional<Usuarios> buscarPorId(int idUsuario) {

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
            WHERE id_usuario = ?
            LIMIT 1
            """;

    try (
            Connection connection =
                    ConnectionDb.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setInt(
                1,
                idUsuario
        );

        try (ResultSet rs =
                statement.executeQuery()) {

            if (rs.next()) {
                return Optional.of(
                        mapearUsuario(rs)
                );
            }
        }

    } catch (SQLException e) {

        throw new IllegalStateException(
                "No se pudo consultar el usuario.",
                e
        );
    }

    return Optional.empty();
}


public List<Usuarios> listarClientes() {

    List<Usuarios> usuarios =
            new ArrayList<>();

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
            WHERE id_rol = 2
            ORDER BY nombre, apellido
            """;

    try (
            Connection connection =
                    ConnectionDb.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet rs =
                    statement.executeQuery()
    ) {

        while (rs.next()) {

            usuarios.add(
                    mapearUsuario(rs)
            );
        }

    } catch (SQLException e) {

        throw new IllegalStateException(
                "No se pudieron consultar los clientes.",
                e
        );
    }

    return usuarios;
}


public boolean actualizarEstadoCliente(
        int idUsuario,
        boolean activo
) {

    String sql = """
            UPDATE usuarios
            SET
                activo = ?,
                fecha_actualizacion = CURRENT_TIMESTAMP
            WHERE id_usuario = ?
              AND id_rol = 2
            """;

    try (
            Connection connection =
                    ConnectionDb.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setBoolean(
                1,
                activo
        );

        statement.setInt(
                2,
                idUsuario
        );

        return statement.executeUpdate() == 1;

    } catch (SQLException e) {

        throw new IllegalStateException(
                "No se pudo cambiar el estado del usuario.",
                e
        );
    }
}
}