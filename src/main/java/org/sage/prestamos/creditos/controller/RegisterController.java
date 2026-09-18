package main.java.org.sage.prestamos.creditos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.org.sage.prestamos.creditos.dto.request.UsuarioRequest;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;

/**
 * FXML Controller class para el registro de nuevos usuarios.
 */
public class RegisterController implements Initializable {

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

    private final UsuariosRepository usuariosRepository = new UsuariosRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de la vista
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        // Crear la petición enviando los 6 parámetros requeridos por UsuarioRequest
        // (nombre, apellido, username, email, password, idRol)
        UsuarioRequest request = new UsuarioRequest(nombre, apellido, username, email, password, 2);

        // Guardar en la base de datos a través del repositorio
        boolean guardado = usuariosRepository.guardarUsuario(request);

        if (guardado) {
            lblMensaje.setText("¡Usuario registrado exitosamente!");
            limpiarCampos();
        } else {
            lblMensaje.setText("Error al registrar el usuario.");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
    }
}