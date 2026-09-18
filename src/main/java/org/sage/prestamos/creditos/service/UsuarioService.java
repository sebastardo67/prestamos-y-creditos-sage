package main.java.org.sage.prestamos.creditos.service;

import java.util.Optional;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;
import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;
import main.java.org.sage.prestamos.creditos.security.jdbcrypt.BCrypt;

public class UsuarioService {

    private final UsuariosRepository usuariosRepository;

    public UsuarioService() {
        this.usuariosRepository = new UsuariosRepository();
    }

    public UsuarioService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public UsuarioResponse autenticar(String email, String password) {
   
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }


        Optional<Usuarios> usuarioOpt = usuariosRepository.buscarPorEmail(email.trim());

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

        Usuarios usuario = usuarioOpt.get();

   
        if (!usuario.isActivo()) {
            throw new IllegalStateException("La cuenta se encuentra inactiva o bloqueada. Contacte al administrador.");
        }

        boolean passwordValida = false;
        try {
            passwordValida = BCrypt.checkpw(password, usuario.getPasswordHash());
        } catch (Exception e) {
            throw new IllegalStateException("Error al verificar la seguridad de la contraseña.");
        }

        if (!passwordValida) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

  
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
}