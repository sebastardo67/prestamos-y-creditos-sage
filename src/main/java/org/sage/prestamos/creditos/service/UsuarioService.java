package main.java.org.sage.prestamos.creditos.service;

import java.time.LocalDateTime;
import java.util.Optional;

import main.java.org.sage.prestamos.creditos.dto.request.UsuarioRequest;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;
import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;
import main.java.org.sage.prestamos.creditos.security.jdbcrypt.BCrypt;

public class UsuarioService {

    /*
     * Rol asignado automáticamente
     * a todo usuario que se registra.
     *
     * En la tabla roles:
     *
     * 1 = ADMINISTRADOR
     * 2 = USUARIO
     */
    private static final int ID_ROL_USUARIO = 2;

    private final UsuariosRepository usuariosRepository;

    public UsuarioService() {
        this.usuariosRepository =
                new UsuariosRepository();
    }

    public UsuarioService(
            UsuariosRepository usuariosRepository
    ) {

        this.usuariosRepository =
                usuariosRepository;
    }

    // =========================================================
    // REGISTRAR USUARIO
    // =========================================================

    public void registrar(
            UsuarioRequest request
    ) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "Los datos del usuario son obligatorios."
            );
        }

        String nombre =
                limpiar(request.getNombre());

        String apellido =
                limpiar(request.getApellido());

        String username =
                limpiar(request.getUsername());

        String email =
                limpiar(request.getEmail()).toLowerCase();

        String password =
                request.getPassword();

        // ============================
        // CAMPOS OBLIGATORIOS
        // ============================

        if (nombre.isEmpty()
                || apellido.isEmpty()
                || username.isEmpty()
                || email.isEmpty()
                || password == null
                || password.isBlank()) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios."
            );
        }

        // ============================
        // VALIDAR CORREO
        // ============================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            throw new IllegalArgumentException(
                    "Ingrese un correo electrónico válido."
            );
        }

        // ============================
        // VALIDAR PASSWORD
        // ============================

        if (password.length() < 6) {

            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 6 caracteres."
            );
        }

        // ============================
        // EMAIL DUPLICADO
        // ============================

        if (usuariosRepository
                .buscarPorEmail(email)
                .isPresent()) {

            throw new IllegalArgumentException(
                    "El correo ya está registrado."
            );
        }

        // ============================
        // USERNAME DUPLICADO
        // ============================

        if (usuariosRepository
                .buscarPorUsername(username)
                .isPresent()) {

            throw new IllegalArgumentException(
                    "El nombre de usuario ya está registrado."
            );
        }

        // ============================
        // HASH DE PASSWORD
        // ============================

        String passwordHash =
                BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                );

        // ============================
        // CREAR MODELO
        // ============================

        Usuarios usuario =
                new Usuarios();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordHash);

        usuario.setIdRol(
                ID_ROL_USUARIO
        );

        usuario.setActivo(true);

        LocalDateTime ahora =
                LocalDateTime.now();

        usuario.setFechaCreacion(ahora);
        usuario.setFechaActualizacion(ahora);

        // ============================
        // GUARDAR
        // ============================

        boolean guardado =
                usuariosRepository.guardar(usuario);

        if (!guardado) {

            throw new IllegalStateException(
                    "No se pudo registrar el usuario."
            );
        }
    }

    // =========================================================
    // AUTENTICAR USUARIO
    // =========================================================

    public UsuarioResponse autenticar(
            String email,
            String password
    ) {

        email = limpiar(email)
                .toLowerCase();

        if (email.isEmpty()) {

            throw new IllegalArgumentException(
                    "El correo electrónico es obligatorio."
            );
        }

        if (password == null
                || password.isBlank()) {

            throw new IllegalArgumentException(
                    "La contraseña es obligatoria."
            );
        }

        Optional<Usuarios> usuarioOpt =
                usuariosRepository
                        .buscarPorEmail(email);

        if (usuarioOpt.isEmpty()) {

            throw new IllegalArgumentException(
                    "Correo o contraseña incorrectos."
            );
        }

        Usuarios usuario =
                usuarioOpt.get();

        if (!usuario.isActivo()) {

            throw new IllegalStateException(
                    "La cuenta se encuentra inactiva."
            );
        }

        boolean passwordValida;

        try {

            passwordValida =
                    BCrypt.checkpw(
                            password,
                            usuario.getPasswordHash()
                    );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "No se pudo verificar la contraseña."
            );
        }

        if (!passwordValida) {

            throw new IllegalArgumentException(
                    "Correo o contraseña incorrectos."
            );
        }

        return convertirAResponse(usuario);
    }

    private UsuarioResponse convertirAResponse(
            Usuarios usuario
    ) {

        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getIdRol(),
                usuario.isActivo()
        );
    }

    private String limpiar(
            String valor
    ) {

        return valor == null
                ? ""
                : valor.trim();
    }
}