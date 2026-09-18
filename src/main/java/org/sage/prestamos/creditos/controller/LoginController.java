package main.java.org.sage.prestamos.creditos.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;
import main.java.org.sage.prestamos.creditos.security.jdbcrypt.BCrypt;

/**
 * FXML Controller class para el inicio de sesión de usuarios.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuariosRepository usuariosRepository = new UsuariosRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de la vista de login
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // Validar que los campos no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, ingrese correo y contraseña.");
            return;
        }

        // Buscar el usuario en la base de datos por email
        Optional<Usuarios> usuarioOpt = usuariosRepository.buscarPorEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();

            // Verificar si el usuario está activo
            if (!usuario.isActivo()) {
                lblMensaje.setText("El usuario se encuentra inactivo.");
                return;
            }

            // Verificar la contraseña encriptada usando BCrypt
            if (BCrypt.checkpw(password, usuario.getPasswordHash())) {
                lblMensaje.setText("¡Inicio de sesión exitoso!");
                
                // Aquí puedes agregar la navegación hacia la vista principal/dashboard
                abrirPantallaPrincipal(usuario);
            } else {
                lblMensaje.setText("Contraseña incorrecta.");
            }
        } else {
            lblMensaje.setText("El usuario no existe.");
        }
    }

    private void abrirPantallaPrincipal(Usuarios usuario) {
        // Lógica para cargar el FXML del Dashboard o menú principal
        System.out.println("Bienvenido: " + usuario.getNombre() + " " + usuario.getApellido());
    }
    
    
    
    
    
    
    
}