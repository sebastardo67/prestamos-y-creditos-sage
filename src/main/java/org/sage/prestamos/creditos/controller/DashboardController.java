package main.java.org.sage.prestamos.creditos.controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;

public class DashboardController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblRol;

    private UsuarioResponse usuario;

    public void setUsuario(
            UsuarioResponse usuario
    ) {

        this.usuario = usuario;

        mostrarDatosUsuario();
    }

    private void mostrarDatosUsuario() {

        if (usuario == null) {
            return;
        }

        lblBienvenida.setText(
                "Bienvenido, "
                + usuario.getNombre()
                + " "
                + usuario.getApellido()
        );

        lblUsername.setText(
                "Usuario: "
                + usuario.getUsername()
        );

        lblEmail.setText(
                "Correo: "
                + usuario.getEmail()
        );

        if (usuario.getIdRol() == 1) {

            lblRol.setText(
                    "Rol: Administrador"
            );

        } else if (usuario.getIdRol() == 2) {

            lblRol.setText(
                    "Rol: Usuario"
            );

        } else {

            lblRol.setText(
                    "Rol ID: "
                    + usuario.getIdRol()
            );
        }
    }

    @FXML
    private void handleCerrarSesion(
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

            e.printStackTrace();
        }
    }
}