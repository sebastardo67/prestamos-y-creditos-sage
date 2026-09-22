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

import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;
import main.java.org.sage.prestamos.creditos.service.UsuarioService;

public class LoginController
        implements Initializable {

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
    private void handleLogin(
            ActionEvent event
    ) {

        lblMensaje.setText("");

        String email =
                txtEmail.getText();

        String password =
                txtPassword.getText();

        try {

            UsuarioResponse usuario =
                    usuarioService.autenticar(
                            email,
                            password
                    );

            abrirDashboard(
                    event,
                    usuario
            );

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
    private void handleRegister(
            ActionEvent event
    ) {

        try {

            URL url =
                    getClass().getResource(
                            "/resources/view/register-view.fxml"
                    );

            if (url == null) {

                throw new IOException(
                        "No se encontró register-view.fxml"
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
                    "Sistema de Préstamos - Registro"
            );

            stage.show();

        } catch (IOException e) {

            mostrarError(
                    "No se pudo abrir la pantalla de registro."
            );

            e.printStackTrace();
        }
    }

    private void abrirDashboard(
            ActionEvent event,
            UsuarioResponse usuario
    ) {

        try {

            URL url =
                    getClass().getResource(
                            "/resources/view/dashboard-view.fxml"
                    );

            if (url == null) {

                throw new IOException(
                        "No se encontró dashboard-view.fxml"
                );
            }

            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();

            DashboardController controller =
                    loader.getController();

            controller.setUsuario(
                    usuario
            );

            Stage stage =
                    (Stage) ((Node)
                            event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );
            
            stage.setResizable(true);
stage.setMinWidth(1000);stage.setMinHeight(650);
stage.setWidth(1200);stage.setHeight(760);
stage.centerOnScreen();
            
            

            stage.setTitle(
                    "Sistema de Préstamos"
            );

            stage.show();

        } catch (IOException e) {

            mostrarError(
                    "El usuario inició sesión, pero no se pudo abrir la pantalla principal."
            );

            e.printStackTrace();
        }
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
}