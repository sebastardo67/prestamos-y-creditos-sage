package main.java.org.sage.prestamos.creditos.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class LoginController implements Initializable {

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
        // Inicialización de la vista de Login
    }

    /**
     * Iniciar sesión.
     */
    @FXML
    private void handleLogin(ActionEvent event) {

        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();

        // =========================
        // VALIDAR CAMPOS
        // =========================

        if (email.isEmpty() || password.isEmpty()) {

            lblMensaje.setText(
                    "Por favor, ingrese correo y contraseña."
            );

            return;
        }

        // =========================
        // BUSCAR USUARIO
        // =========================

        Optional<Usuarios> usuarioOpt =
                usuariosRepository.buscarPorEmail(email);

        if (usuarioOpt.isPresent()) {

            Usuarios usuario = usuarioOpt.get();

            // =========================
            // VERIFICAR ESTADO
            // =========================

            if (!usuario.isActivo()) {

                lblMensaje.setText(
                        "El usuario se encuentra inactivo."
                );

                return;
            }

            // =========================
            // VERIFICAR CONTRASEÑA
            // =========================

            if (BCrypt.checkpw(
                    password,
                    usuario.getPasswordHash())) {

                lblMensaje.setText(
                        "¡Inicio de sesión exitoso!"
                );

                abrirPantallaPrincipal(usuario);

            } else {

                lblMensaje.setText(
                        "Contraseña incorrecta."
                );
            }

        } else {

            lblMensaje.setText(
                    "El usuario no existe."
            );
        }
    }

    /**
     * Abrir pantalla principal.
     *
     * Por ahora solamente muestra el usuario
     * en la consola.
     */
    private void abrirPantallaPrincipal(Usuarios usuario) {

        System.out.println(
                "Bienvenido: "
                + usuario.getNombre()
                + " "
                + usuario.getApellido()
        );

        /*
         * Aquí posteriormente podemos cargar
         * el dashboard/pantalla principal.
         */
    }

    /**
     * Abrir pantalla de Registro.
     */
    @FXML
    private void handleRegister(ActionEvent event) {

        try {

            URL url = getClass().getResource(
                    "/view/register-view.fxml"
            );

            if (url == null) {

                throw new IOException(
                        "No se encontró /view/register-view.fxml"
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

            lblMensaje.setText(
                    "No se pudo abrir la pantalla de registro."
            );
        }
    }
}