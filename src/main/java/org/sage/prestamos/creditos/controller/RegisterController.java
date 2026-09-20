package main.java.org.sage.prestamos.creditos.controller;

import java.io.IOException;
import java.net.URL;
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

import main.java.org.sage.prestamos.creditos.dto.request.UsuarioRequest;
import main.java.org.sage.prestamos.creditos.service.UsuarioService;

public class RegisterController
        implements Initializable {

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

    private final UsuarioService usuarioService =
            new UsuarioService();

    @Override
    public void initialize(
            URL url,
            ResourceBundle resourceBundle
    ) {

    }

    @FXML
    private void handleRegister(
            ActionEvent event
    ) {

        limpiarMensaje();

        UsuarioRequest request =
                new UsuarioRequest(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtUsername.getText(),
                        txtEmail.getText(),
                        txtPassword.getText()
                );

        try {

            usuarioService.registrar(request);

            mostrarExito(
                    "¡Usuario registrado correctamente!"
            );

            limpiarCampos();

        } catch (IllegalArgumentException e) {

            mostrarError(
                    e.getMessage()
            );

        } catch (IllegalStateException e) {

            mostrarError(
                    e.getMessage()
            );

            e.printStackTrace();

        } catch (Exception e) {

            mostrarError(
                    "Ocurrió un error inesperado."
            );

            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(
            ActionEvent event
    ) {

        try {

            URL url =
                    getClass().getResource(
                            "/resources/view/login-view.fxml"
                    );

            if (url == null) {

                throw new IOException(
                        "No se encontró login-view.fxml"
                );
            }

            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();

            Stage stage =
                    (Stage) ((Node)
                            event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "Sistema de Préstamos - Inicio de sesión"
            );

            stage.show();

        } catch (IOException e) {

            mostrarError(
                    "No se pudo abrir el inicio de sesión."
            );

            e.printStackTrace();
        }
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtApellido.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    private void limpiarMensaje() {

        lblMensaje.setText("");
        lblMensaje.setStyle("");
    }

    private void mostrarError(
            String mensaje
    ) {

        lblMensaje.setStyle(
                "-fx-text-fill: red;"
        );

        lblMensaje.setText(
                mensaje
        );
    }

    private void mostrarExito(
            String mensaje
    ) {

        lblMensaje.setStyle(
                "-fx-text-fill: green;"
        );

        lblMensaje.setText(
                mensaje
        );
    }
}