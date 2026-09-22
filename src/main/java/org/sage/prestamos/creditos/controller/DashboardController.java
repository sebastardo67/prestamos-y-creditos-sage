package main.java.org.sage.prestamos.creditos.controller;
 
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
 
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
import javafx.scene.control.cell.PropertyValueFactory;
 
import main.java.org.sage.prestamos.creditos.dto.request.PrestamoRequest;
import main.java.org.sage.prestamos.creditos.dto.response.PrestamoResponse;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioResponse;
import main.java.org.sage.prestamos.creditos.service.PrestamoService;
 
public class DashboardController {
 
    @FXML
    private MenuButton btnUsuario;
 
    @FXML
    private Label lblBienvenida;
 
    @FXML
    private Label lblNombre;
 
    @FXML
    private Label lblUsername;
 
    @FXML
    private Label lblEmail;
 
    @FXML
    private Label lblRol;
 
    @FXML
    private ScrollPane inicioPane;
 
    @FXML
    private ScrollPane catalogoPane;
 
    @FXML
    private ScrollPane formularioPane;
 
    @FXML
    private VBox misPrestamosPane;
 
    @FXML
    private Label lblProductoSeleccionado;
 
    @FXML
    private Label lblTasaProducto;
 
    @FXML
    private Label lblRangoProducto;
 
    @FXML
    private TextField txtNombreSolicitud;
 
    @FXML
    private TextField txtTelefono;
 
    @FXML
    private TextField txtIngresoMensual;
 
    @FXML
    private TextField txtMonto;
 
    @FXML
    private TextField txtPlazo;
 
    @FXML
    private TextArea txtMotivo;
 
    @FXML
    private Label lblFormularioMensaje;
 
    @FXML
    private TableView<PrestamoResponse> tblPrestamos;
 
    @FXML
    private TableColumn<PrestamoResponse, String> colTipo;
 
    @FXML
    private TableColumn<PrestamoResponse, BigDecimal> colMonto;
 
    @FXML
    private TableColumn<PrestamoResponse, Integer> colPlazo;
 
    @FXML
    private TableColumn<PrestamoResponse, BigDecimal> colTasa;
 
    @FXML
    private TableColumn<PrestamoResponse, BigDecimal> colCuota;
 
    @FXML
    private TableColumn<PrestamoResponse, String> colEstado;
 
    @FXML
    private TableColumn<PrestamoResponse, LocalDate> colFecha;
 
    @FXML
    private Label lblSinPrestamos;
 
    private UsuarioResponse usuario;
 
    private final PrestamoService prestamoService =
            new PrestamoService();
 
    private String tipoPrestamoSeleccionado;
 
    @FXML
    public void initialize() {
 
        configurarTabla();
 
        mostrarPanel(
                inicioPane
        );
    }
 
    public void setUsuario(
            UsuarioResponse usuario
    ) {
 
        this.usuario = usuario;
 
        btnUsuario.setText(
                usuario.getUsername()
        );
 
        lblBienvenida.setText(
                "Bienvenido, "
                + usuario.getNombre()
        );
 
        lblNombre.setText(
                usuario.getNombre()
                + " "
                + usuario.getApellido()
        );
 
        lblUsername.setText(
                usuario.getUsername()
        );
 
        lblEmail.setText(
                usuario.getEmail()
        );
 
        lblRol.setText(
                usuario.getIdRol() == 1
                ? "Administrador"
                : "Usuario"
        );
 
        txtNombreSolicitud.setText(
                usuario.getNombre()
                + " "
                + usuario.getApellido()
        );
    }
 
    @FXML
    private void handleInicio() {
 
        mostrarPanel(
                inicioPane
        );
    }
 
    @FXML
    private void handleCatalogo() {
 
        mostrarPanel(
                catalogoPane
        );
    }
 
    @FXML
    private void handleMisPrestamos() {
 
        cargarPrestamos();
 
        mostrarPanel(
                misPrestamosPane
        );
    }
 
    @FXML
    private void seleccionarPersonal() {
 
        seleccionarProducto(
                "Crédito Personal",
                "14.50% anual",
                "Q1,000 - Q50,000 | 6 a 36 meses"
        );
    }
 
    @FXML
    private void seleccionarEstudiantil() {
 
        seleccionarProducto(
                "Crédito Estudiantil",
                "9.50% anual",
                "Q1,000 - Q75,000 | 12 a 48 meses"
        );
    }
 
    @FXML
    private void seleccionarVehicular() {
 
        seleccionarProducto(
                "Crédito Vehicular",
                "11.00% anual",
                "Q5,000 - Q150,000 | 12 a 60 meses"
        );
    }
 
    @FXML
    private void seleccionarEmprendedor() {
 
        seleccionarProducto(
                "Crédito Emprendedor",
                "13.00% anual",
                "Q2,000 - Q100,000 | 6 a 48 meses"
        );
    }
 
    private void seleccionarProducto(
            String tipo,
            String tasa,
            String rango
    ) {
 
        tipoPrestamoSeleccionado =
                tipo;
 
        lblProductoSeleccionado.setText(
                tipo
        );
 
        lblTasaProducto.setText(
                tasa
        );
 
        lblRangoProducto.setText(
                rango
        );
 
        lblFormularioMensaje.setText("");
 
        mostrarPanel(
                formularioPane
        );
    }
 
    @FXML
    private void handleEnviarSolicitud() {
 
        lblFormularioMensaje.setText("");
 
        if (tipoPrestamoSeleccionado == null) {
 
            mostrarError(
                    "Debe seleccionar un producto."
            );
 
            return;
        }
 
        try {
 
            BigDecimal ingreso =
                    convertirDecimal(
                            txtIngresoMensual.getText(),
                            "ingreso mensual"
                    );
 
            BigDecimal monto =
                    convertirDecimal(
                            txtMonto.getText(),
                            "monto"
                    );
 
            int plazo;
 
            try {
 
                plazo =
                        Integer.parseInt(
                                txtPlazo
                                        .getText()
                                        .trim()
                        );
 
            } catch (NumberFormatException e) {
 
                throw new IllegalArgumentException(
                        "Ingrese un plazo válido en meses."
                );
            }
 
            PrestamoRequest request =
                    new PrestamoRequest(
                            usuario.getIdUsuario(),
                            tipoPrestamoSeleccionado,
                            txtTelefono.getText(),
                            ingreso,
                            monto,
                            plazo,
                            txtMotivo.getText()
                    );
 
            prestamoService
                    .solicitarPrestamo(
                            request
                    );
 
            mostrarExito(
                    "Solicitud registrada correctamente."
            );
 
            limpiarFormulario();
 
            cargarPrestamos();
 
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
    private void volverCatalogo() {
 
        mostrarPanel(
                catalogoPane
        );
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
                    (Stage) btnUsuario
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
 
    private void cargarPrestamos() {
 
        if (usuario == null) {
            return;
        }
 
        List<PrestamoResponse> prestamos =
                prestamoService
                        .listarPorUsuario(
                                usuario.getIdUsuario()
                        );
 
        tblPrestamos.setItems(
                FXCollections.observableArrayList(
                        prestamos
                )
        );
 
        lblSinPrestamos.setVisible(
                prestamos.isEmpty()
        );
 
        lblSinPrestamos.setManaged(
                prestamos.isEmpty()
        );
    }
 
    private void configurarTabla() {
 
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
 
        colPlazo.setCellValueFactory(
                new PropertyValueFactory<>(
                        "plazoMeses"
                )
        );
 
        colTasa.setCellValueFactory(
                new PropertyValueFactory<>(
                        "tasaInteres"
                )
        );
 
        colCuota.setCellValueFactory(
                new PropertyValueFactory<>(
                        "cuotaMensual"
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
                column -> crearCeldaMoneda()
        );
 
        colCuota.setCellFactory(
                column -> crearCeldaMoneda()
        );
 
        colTasa.setCellFactory(
                column -> new TableCell<>() {
 
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
                                : item + "%"
                        );
                    }
                }
        );
    }
 
    private TableCell<PrestamoResponse, BigDecimal>
            crearCeldaMoneda() {
 
        return new TableCell<>() {
 
            @Override
            protected void updateItem(
                    BigDecimal item,
                    boolean empty
            ) {
 
                super.updateItem(
                        item,
                        empty
                );
 
                if (empty || item == null) {
 
                    setText("");
 
                } else {
 
                    setText(
                            "Q "
                            + item.setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            ).toPlainString()
                    );
                }
            }
        };
    }
 
    private BigDecimal convertirDecimal(
            String valor,
            String campo
    ) {
 
        try {
 
            String limpio =
                    valor
                            .replace("Q", "")
                            .replace(",", "")
                            .trim();
 
            return new BigDecimal(
                    limpio
            );
 
        } catch (Exception e) {
 
            throw new IllegalArgumentException(
                    "Ingrese un "
                    + campo
                    + " válido."
            );
        }
    }
 
    private void mostrarPanel(
            Object panel
    ) {
 
        inicioPane.setVisible(
                panel == inicioPane
        );
 
        inicioPane.setManaged(
                panel == inicioPane
        );
 
        catalogoPane.setVisible(
                panel == catalogoPane
        );
 
        catalogoPane.setManaged(
                panel == catalogoPane
        );
 
        formularioPane.setVisible(
                panel == formularioPane
        );
 
        formularioPane.setManaged(
                panel == formularioPane
        );
 
        misPrestamosPane.setVisible(
                panel == misPrestamosPane
        );
 
        misPrestamosPane.setManaged(
                panel == misPrestamosPane
        );
    }
 
    private void limpiarFormulario() {
 
        txtTelefono.clear();
        txtIngresoMensual.clear();
        txtMonto.clear();
        txtPlazo.clear();
        txtMotivo.clear();
    }
 
    private void mostrarError(
            String mensaje
    ) {
 
        lblFormularioMensaje.setStyle(
                "-fx-text-fill: #C62828;"
        );
 
        lblFormularioMensaje.setText(
                mensaje
        );
    }
 
    private void mostrarExito(
            String mensaje
    ) {
 
        lblFormularioMensaje.setStyle(
                "-fx-text-fill: #208444;"
        );
 
        lblFormularioMensaje.setText(
                mensaje
        );
    }
}