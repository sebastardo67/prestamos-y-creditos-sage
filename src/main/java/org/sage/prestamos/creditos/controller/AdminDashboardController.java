package main.java.org.sage.prestamos.creditos.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.org.sage.prestamos.creditos.dto.response.AdminResumeResponse;
import main.java.org.sage.prestamos.creditos.dto.response.PrestamoAdminResponse;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioAdminResponse;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;
import main.java.org.sage.prestamos.creditos.service.AdminService;

public class AdminDashboardController {

    @FXML
    private MenuButton btnAdmin;

    @FXML
    private VBox resumenPane;

    @FXML
    private VBox solicitudesPane;

    @FXML
    private VBox clientesPane;


    // RESUMEN

    @FXML
    private Label lblTotalSolicitudes;

    @FXML
    private Label lblPendientes;

    @FXML
    private Label lblAprobados;

    @FXML
    private Label lblRechazados;

    @FXML
    private Label lblMontoAprobado;


    // SOLICITUDES

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private TableView<PrestamoAdminResponse>
            tblSolicitudes;

    @FXML
    private TableColumn<PrestamoAdminResponse, Integer>
            colIdPrestamo;

    @FXML
    private TableColumn<PrestamoAdminResponse, String>
            colCliente;

    @FXML
    private TableColumn<PrestamoAdminResponse, String>
            colTipo;

    @FXML
    private TableColumn<PrestamoAdminResponse, BigDecimal>
            colMonto;

    @FXML
    private TableColumn<PrestamoAdminResponse, String>
            colEstado;

    @FXML
    private TableColumn<PrestamoAdminResponse, Object>
            colFecha;


    // DETALLE

    @FXML
    private Label lblDetalleCliente;

    @FXML
    private Label lblDetalleEmail;

    @FXML
    private Label lblDetalleTelefono;

    @FXML
    private Label lblDetalleIngreso;

    @FXML
    private Label lblDetalleMonto;

    @FXML
    private Label lblDetallePlazo;

    @FXML
    private Label lblDetalleTasa;

    @FXML
    private Label lblDetalleCuota;

    @FXML
    private Label lblDetalleMotivo;

    @FXML
    private Label lblDetalleEstado;

    @FXML
    private Label lblDetalleComentario;

    @FXML
    private Button btnAprobar;

    @FXML
    private Button btnRechazar;


    // CLIENTES

    @FXML
    private TableView<UsuarioAdminResponse>
            tblClientes;

    @FXML
    private TableColumn<UsuarioAdminResponse, Integer>
            colClienteId;

    @FXML
    private TableColumn<UsuarioAdminResponse, String>
            colClienteNombre;

    @FXML
    private TableColumn<UsuarioAdminResponse, String>
            colClienteUsername;

    @FXML
    private TableColumn<UsuarioAdminResponse, String>
            colClienteEmail;

    @FXML
    private TableColumn<UsuarioAdminResponse, Boolean>
            colClienteActivo;

    @FXML
    private Button btnCambiarEstadoCliente;


    private UsuarioResponse administrador;

    private final AdminService adminService =
            new AdminService();


    @FXML
    public void initialize() {

        configurarTablaSolicitudes();
        configurarTablaClientes();

        cmbEstado.setItems(
                FXCollections.observableArrayList(
                        "TODOS",
                        "PENDIENTE",
                        "APROBADO",
                        "RECHAZADO"
                )
        );

        cmbEstado.setValue(
                "TODOS"
        );

        cmbEstado.setOnAction(
                event ->
                        cargarSolicitudes()
        );

        tblSolicitudes
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, actual) ->
                                mostrarDetalle(
                                        actual
                                )
                );

        tblClientes
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, actual) ->
                                actualizarBotonCliente(
                                        actual
                                )
                );

        mostrarPanel(
                resumenPane
        );
    }


    public void setUsuario(
            UsuarioResponse administrador
    ) {

        this.administrador =
                administrador;

        btnAdmin.setText(
                administrador.getUsername()
        );

        actualizarTodo();
    }


    @FXML
    private void handleResumen() {

        cargarResumen();

        mostrarPanel(
                resumenPane
        );
    }


    @FXML
    private void handleSolicitudes() {

        cargarSolicitudes();

        mostrarPanel(
                solicitudesPane
        );
    }


    @FXML
    private void handleClientes() {

        cargarClientes();

        mostrarPanel(
                clientesPane
        );
    }


    @FXML
    private void handleAprobar() {

        PrestamoAdminResponse prestamo =
                tblSolicitudes
                        .getSelectionModel()
                        .getSelectedItem();

        if (prestamo == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Seleccione una solicitud."
            );

            return;
        }

        if (!"PENDIENTE".equalsIgnoreCase(
                prestamo.getEstado())) {

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Esta solicitud ya fue resuelta."
            );

            return;
        }

        if (!confirmar(
                "Aprobar préstamo",
                "¿Desea aprobar la solicitud #"
                + prestamo.getIdPrestamo()
                + "?"
        )) {
            return;
        }

        try {

            adminService.aprobarPrestamo(
                    administrador.getIdUsuario(),
                    prestamo.getIdPrestamo()
            );

            actualizarTodo();

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Préstamo aprobado correctamente."
            );

        } catch (Exception e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }


    @FXML
    private void handleRechazar() {

        PrestamoAdminResponse prestamo =
                tblSolicitudes
                        .getSelectionModel()
                        .getSelectedItem();

        if (prestamo == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Seleccione una solicitud."
            );

            return;
        }

        if (!"PENDIENTE".equalsIgnoreCase(
                prestamo.getEstado())) {

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Esta solicitud ya fue resuelta."
            );

            return;
        }

        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle(
                "Rechazar préstamo"
        );

        dialog.setHeaderText(
                "Solicitud #"
                + prestamo.getIdPrestamo()
        );

        dialog.setContentText(
                "Motivo del rechazo:"
        );

        Optional<String> resultado =
                dialog.showAndWait();

        if (resultado.isEmpty()) {
            return;
        }

        try {

            adminService.rechazarPrestamo(
                    administrador.getIdUsuario(),
                    prestamo.getIdPrestamo(),
                    resultado.get()
            );

            actualizarTodo();

            mostrarMensaje(
                    Alert.AlertType.INFORMATION,
                    "Solicitud rechazada."
            );

        } catch (Exception e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }


    @FXML
    private void handleCambiarEstadoCliente() {

        UsuarioAdminResponse cliente =
                tblClientes
                        .getSelectionModel()
                        .getSelectedItem();

        if (cliente == null) {

            mostrarMensaje(
                    Alert.AlertType.WARNING,
                    "Seleccione un cliente."
            );

            return;
        }

        boolean nuevoEstado =
                !cliente.isActivo();

        String accion =
                nuevoEstado
                ? "activar"
                : "inactivar";

        if (!confirmar(
                "Cambiar estado",
                "¿Desea "
                + accion
                + " al usuario "
                + cliente.getUsername()
                + "?"
        )) {
            return;
        }

        try {

            adminService
                    .cambiarEstadoCliente(
                            administrador.getIdUsuario(),
                            cliente.getIdUsuario(),
                            nuevoEstado
                    );

            cargarClientes();

        } catch (Exception e) {

            mostrarMensaje(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }


    @FXML
    private void handleCerrarSesion() {

        try {

            URL url =
                    getClass().getResource(
                            "/resources/view/login-view.fxml"
                    );

            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();

            Stage stage =
                    (Stage) btnAdmin
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root)
            );

            stage.setTitle(
                    "Sistema de Préstamos - Inicio de sesión"
            );

            stage.setMinWidth(600);
            stage.setMinHeight(420);

            stage.sizeToScene();
            stage.centerOnScreen();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    private void actualizarTodo() {

        if (administrador == null) {
            return;
        }

        cargarResumen();
        cargarSolicitudes();
        cargarClientes();
    }


    private void cargarResumen() {

        if (administrador == null) {
            return;
        }

        AdminResumeResponse resumen =
                adminService.obtenerResumen(
                        administrador.getIdUsuario()
                );

        lblTotalSolicitudes.setText(
                String.valueOf(
                        resumen.getTotalSolicitudes()
                )
        );

        lblPendientes.setText(
                String.valueOf(
                        resumen.getPendientes()
                )
        );

        lblAprobados.setText(
                String.valueOf(
                        resumen.getAprobados()
                )
        );

        lblRechazados.setText(
                String.valueOf(
                        resumen.getRechazados()
                )
        );

        lblMontoAprobado.setText(
                "Q "
                + resumen
                        .getMontoTotalAprobado()
                        .toPlainString()
        );
    }


    private void cargarSolicitudes() {

        if (administrador == null) {
            return;
        }

        List<PrestamoAdminResponse> lista =
                adminService.listarSolicitudes(
                        administrador.getIdUsuario(),
                        cmbEstado.getValue()
                );

        tblSolicitudes.setItems(
                FXCollections.observableArrayList(
                        lista
                )
        );

        mostrarDetalle(null);
    }


    private void cargarClientes() {

        if (administrador == null) {
            return;
        }

        tblClientes.setItems(
                FXCollections.observableArrayList(
                        adminService.listarClientes(
                                administrador.getIdUsuario()
                        )
                )
        );
    }


    private void mostrarDetalle(
            PrestamoAdminResponse prestamo
    ) {

        boolean seleccionado =
                prestamo != null;

        btnAprobar.setDisable(
                !seleccionado
                || !"PENDIENTE".equalsIgnoreCase(
                        prestamo == null
                        ? ""
                        : prestamo.getEstado()
                )
        );

        btnRechazar.setDisable(
                btnAprobar.isDisable()
        );

        if (prestamo == null) {

            lblDetalleCliente.setText("-");
            lblDetalleEmail.setText("-");
            lblDetalleTelefono.setText("-");
            lblDetalleIngreso.setText("-");
            lblDetalleMonto.setText("-");
            lblDetallePlazo.setText("-");
            lblDetalleTasa.setText("-");
            lblDetalleCuota.setText("-");
            lblDetalleMotivo.setText("-");
            lblDetalleEstado.setText("-");
            lblDetalleComentario.setText("-");

            return;
        }

        lblDetalleCliente.setText(
                prestamo.getNombreCliente()
        );

        lblDetalleEmail.setText(
                prestamo.getEmail()
        );

        lblDetalleTelefono.setText(
                prestamo.getTelefono()
        );

        lblDetalleIngreso.setText(
                "Q "
                + prestamo
                        .getIngresoMensual()
                        .toPlainString()
        );

        lblDetalleMonto.setText(
                "Q "
                + prestamo
                        .getMonto()
                        .toPlainString()
        );

        lblDetallePlazo.setText(
                prestamo.getPlazoMeses()
                + " meses"
        );

        lblDetalleTasa.setText(
                prestamo.getTasaInteres()
                + "%"
        );

        lblDetalleCuota.setText(
                "Q "
                + prestamo
                        .getCuotaMensual()
                        .toPlainString()
        );

        lblDetalleMotivo.setText(
                prestamo.getMotivo()
        );

        lblDetalleEstado.setText(
                prestamo.getEstado()
        );

        lblDetalleComentario.setText(
                prestamo.getComentarioAdmin() == null
                ? "-"
                : prestamo.getComentarioAdmin()
        );
    }


    private void configurarTablaSolicitudes() {

        colIdPrestamo.setCellValueFactory(
                new PropertyValueFactory<>(
                        "idPrestamo"
                )
        );

        colCliente.setCellValueFactory(
                new PropertyValueFactory<>(
                        "nombreCliente"
                )
        );

        colTipo.setCellValueFactory(
                new PropertyValueFactory<>(
                        "tipoPrestamo"
                )
        );

        colMonto.setCellValueFactory(
                new PropertyValueFactory<>(
                        "monto"
                )
        );

        colEstado.setCellValueFactory(
                new PropertyValueFactory<>(
                        "estado"
                )
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>(
                        "fecha"
                )
        );

        colMonto.setCellFactory(
                column ->
                        new TableCell<>() {

                            @Override
                            protected void updateItem(
                                    BigDecimal item,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        item,
                                        empty
                                );

                                setText(
                                        empty || item == null
                                        ? ""
                                        : "Q "
                                          + item.toPlainString()
                                );
                            }
                        }
        );
    }


    private void configurarTablaClientes() {

        colClienteId.setCellValueFactory(
                new PropertyValueFactory<>(
                        "idUsuario"
                )
        );

        colClienteNombre.setCellValueFactory(
                new PropertyValueFactory<>(
                        "nombreCompleto"
                )
        );

        colClienteUsername.setCellValueFactory(
                new PropertyValueFactory<>(
                        "username"
                )
        );

        colClienteEmail.setCellValueFactory(
                new PropertyValueFactory<>(
                        "email"
                )
        );

        colClienteActivo.setCellValueFactory(
                new PropertyValueFactory<>(
                        "activo"
                )
        );

        colClienteActivo.setCellFactory(
                column ->
                        new TableCell<>() {

                            @Override
                            protected void updateItem(
                                    Boolean activo,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        activo,
                                        empty
                                );

                                if (empty
                                        || activo == null) {

                                    setText("");

                                } else {

                                    setText(
                                            activo
                                            ? "Activo"
                                            : "Inactivo"
                                    );
                                }
                            }
                        }
        );
    }


    private void actualizarBotonCliente(
            UsuarioAdminResponse cliente
    ) {

        if (cliente == null) {

            btnCambiarEstadoCliente
                    .setDisable(true);

            btnCambiarEstadoCliente
                    .setText(
                            "Cambiar estado"
                    );

            return;
        }

        btnCambiarEstadoCliente
                .setDisable(false);

        btnCambiarEstadoCliente
                .setText(
                        cliente.isActivo()
                        ? "Inactivar usuario"
                        : "Activar usuario"
                );
    }


    private void mostrarPanel(
            VBox panel
    ) {

        resumenPane.setVisible(
                panel == resumenPane
        );

        resumenPane.setManaged(
                panel == resumenPane
        );

        solicitudesPane.setVisible(
                panel == solicitudesPane
        );

        solicitudesPane.setManaged(
                panel == solicitudesPane
        );

        clientesPane.setVisible(
                panel == clientesPane
        );

        clientesPane.setManaged(
                panel == clientesPane
        );
    }


    private boolean confirmar(
            String titulo,
            String mensaje
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        return alert.showAndWait()
                .filter(
                        boton ->
                                boton
                                == ButtonType.OK
                )
                .isPresent();
    }


    private void mostrarMensaje(
            Alert.AlertType tipo,
            String mensaje
    ) {

        Alert alert =
                new Alert(tipo);

        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}