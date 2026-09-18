/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.java.org.sage.prestamos.creditos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author gabpa
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización del controlador
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Debe ingresar correo y contraseña.");
            return;
        }

        lblMensaje.setText("Datos ingresados correctamente.");
    }
}