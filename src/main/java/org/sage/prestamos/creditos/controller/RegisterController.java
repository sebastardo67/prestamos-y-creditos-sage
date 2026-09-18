package main.java.org.sage.prestamos.creditos.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;
import main.java.org.sage.prestamos.creditos.security.jdbcrypt.BCrypt;

public class RegisterController implements javafx.fxml.Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuariosRepository usuariosRepository =
            new UsuariosRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización del formulario
    }

    /**
     * Registrar nuevo usuario.
     */
    @FXML
    private void handleRegister(ActionEvent event) {

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();

        // =========================
        // VALIDAR CAMPOS
        // =========================

        if (nombre.isEmpty()
                || apellido.isEmpty()
                || username.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        // =========================
        // VALIDAR EMAIL
        // =========================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            mostrarMensaje("Ingrese un correo electrónico válido.");
            return;
        }

        // =========================
        // VALIDAR CONTRASEÑA
        // =========================

        if (password.length() < 6) {

            mostrarMensaje(
                    "La contraseña debe tener al menos 6 caracteres."
            );

            return;
        }

        // =========================
        // VERIFICAR EMAIL
        // =========================

        if (usuariosRepository.buscarPorEmail(email).isPresent()) {

            mostrarMensaje("El correo ya está registrado.");
            return;
        }

        // =========================
        // VERIFICAR USERNAME
        // =========================

        if (usuariosRepository.buscarPorUsername(username).isPresent()) {

            mostrarMensaje(
                    "El nombre de usuario ya está registrado."
            );

            return;
        }

        // =========================
        // ENCRIPTAR CONTRASEÑA
        // =========================

        String passwordHash = BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );

        // =========================
        // CREAR USUARIO
        // =========================

        Usuarios usuario = new Usuarios();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordHash);

        /*
         * ID DEL ROL
         *
         * 2 = usuario normal
         *
         * Verifica que en tu tabla de roles
         * este ID corresponda al usuario normal.
         */
        usuario.setIdRol(2);

        usuario.setActivo(true);

        LocalDateTime ahora = LocalDateTime.now();

        usuario.setFechaCreacion(ahora);
        usuario.setFechaActualizacion(ahora);

        // =========================
        // GUARDAR EN BASE DE DATOS
        // =========================

        boolean registrado = usuariosRepository.guardar(usuario);

        if (registrado) {

            mostrarMensaje(
                    "¡Usuario registrado correctamente!"
            );

            limpiarCampos();

        } else {

            mostrarMensaje(
                    "No se pudo registrar el usuario."
            );
        }
    }

    /**
     * Regresar a la pantalla de Login.
     */
    @FXML
    private void handleLogin(ActionEvent event) {

        try {

            URL url = getClass().getResource(
                    "/view/login-view.fxml"
            );

            if (url == null) {

                throw new IOException(
                        "No se encontró /view/login-view.fxml"
                );
            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Stage stage = (Stage)
                    ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

            mostrarMensaje(
                    "No se pudo abrir el inicio de sesión."
            );
        }
    }

    /**
     * Limpiar formulario.
     */
    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    /**
     * Mostrar mensaje al usuario.
     */
    private void mostrarMensaje(String mensaje) {

        lblMensaje.setText(mensaje);
    }
}