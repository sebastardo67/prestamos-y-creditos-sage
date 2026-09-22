package main.java.org.sage.prestamos.creditos.service;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
 
import main.java.org.sage.prestamos.creditos.dto.request.PrestamoRequest;
import main.java.org.sage.prestamos.creditos.dto.response.PrestamoResponse;
import main.java.org.sage.prestamos.creditos.model.Prestamos;
import main.java.org.sage.prestamos.creditos.repository.PrestamosRepository;
 
public class PrestamoService {
 
    private final PrestamosRepository prestamosRepository;
 
    public PrestamoService() {
        this.prestamosRepository =
                new PrestamosRepository();
    }
 
    public void solicitarPrestamo(
            PrestamoRequest request
    ) {
 
        if (request == null) {
            throw new IllegalArgumentException(
                    "Los datos de la solicitud son obligatorios."
            );
        }
 
        if (request.getIdUsuario() <= 0) {
            throw new IllegalArgumentException(
                    "Usuario inválido."
            );
        }
 
        String telefono = limpiar(
                request.getTelefono()
        );
 
        if (!telefono.matches(
                "^[0-9 +()\\-]{8,20}$"
        )) {
 
            throw new IllegalArgumentException(
                    "Ingrese un número de teléfono válido."
            );
        }
 
        if (request.getIngresoMensual() == null
                || request.getIngresoMensual()
                        .compareTo(BigDecimal.ZERO) <= 0) {
 
            throw new IllegalArgumentException(
                    "El ingreso mensual debe ser mayor que cero."
            );
        }
 
        if (request.getMonto() == null
                || request.getMonto()
                        .compareTo(BigDecimal.ZERO) <= 0) {
 
            throw new IllegalArgumentException(
                    "Ingrese un monto válido."
            );
        }
 
        String motivo =
                limpiar(request.getMotivo());
 
        if (motivo.isEmpty()) {
 
            throw new IllegalArgumentException(
                    "Debe indicar el motivo del préstamo."
            );
        }
 
        ReglaPrestamo regla =
                obtenerRegla(
                        request.getTipoPrestamo()
                );
 
        if (request.getMonto()
                .compareTo(regla.montoMinimo()) < 0
                || request.getMonto()
                .compareTo(regla.montoMaximo()) > 0) {
 
            throw new IllegalArgumentException(
                    "El monto solicitado no está dentro del rango permitido."
            );
        }
 
        if (request.getPlazoMeses()
< regla.plazoMinimo()
                || request.getPlazoMeses()
> regla.plazoMaximo()) {
 
            throw new IllegalArgumentException(
                    "El plazo seleccionado no está permitido para este préstamo."
            );
        }
 
        BigDecimal cuota =
                calcularCuotaMensual(
                        request.getMonto(),
                        regla.tasaAnual(),
                        request.getPlazoMeses()
                );
 
        /*
         * Para esta simulación bancaria se evita
         * que la cuota supere el 40 % del ingreso.
         */
        BigDecimal limiteCuota =
                request.getIngresoMensual()
                        .multiply(
                                new BigDecimal("0.40")
                        );
 
        if (cuota.compareTo(limiteCuota) > 0) {
 
            throw new IllegalArgumentException(
                    "La cuota estimada supera el 40% de sus ingresos mensuales."
            );
        }
 
        Prestamos prestamo =
                new Prestamos();
 
        prestamo.setIdUsuario(
                request.getIdUsuario()
        );
 
        prestamo.setTipoPrestamo(
                request.getTipoPrestamo()
        );
 
        prestamo.setTelefono(
                telefono
        );
 
        prestamo.setIngresoMensual(
                request.getIngresoMensual()
        );
 
        prestamo.setMonto(
                request.getMonto()
        );
 
        prestamo.setFecha(
                LocalDate.now()
        );
 
        prestamo.setPlazoMeses(
                request.getPlazoMeses()
        );
 
        prestamo.setTasaInteres(
                regla.tasaAnual()
        );
 
        prestamo.setCuotaMensual(
                cuota
        );
 
        prestamo.setEstado(
                "PENDIENTE"
        );
 
        prestamo.setMotivo(
                motivo
        );
 
        boolean guardado =
                prestamosRepository.guardar(
                        prestamo
                );
 
        if (!guardado) {
 
            throw new IllegalStateException(
                    "No se pudo registrar la solicitud."
            );
        }
    }
 
    public List<PrestamoResponse> listarPorUsuario(
            int idUsuario
    ) {
 
        return prestamosRepository
                .listarPorUsuario(idUsuario)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }
 
    private PrestamoResponse convertirAResponse(
            Prestamos prestamo
    ) {
 
        return new PrestamoResponse(
                prestamo.getIdPrestamo(),
                prestamo.getTipoPrestamo(),
                prestamo.getMonto(),
                prestamo.getFecha(),
                prestamo.getPlazoMeses(),
                prestamo.getTasaInteres(),
                prestamo.getCuotaMensual(),
                prestamo.getEstado()
        );
    }
 
    private ReglaPrestamo obtenerRegla(
            String tipo
    ) {
 
        return switch (tipo) {
 
            case "Crédito Personal" ->
                new ReglaPrestamo(
                        new BigDecimal("14.50"),
                        new BigDecimal("1000"),
                        new BigDecimal("50000"),
                        6,
                        36
                );
 
            case "Crédito Estudiantil" ->
                new ReglaPrestamo(
                        new BigDecimal("9.50"),
                        new BigDecimal("1000"),
                        new BigDecimal("75000"),
                        12,
                        48
                );
 
            case "Crédito Vehicular" ->
                new ReglaPrestamo(
                        new BigDecimal("11.00"),
                        new BigDecimal("5000"),
                        new BigDecimal("150000"),
                        12,
                        60
                );
 
            case "Crédito Emprendedor" ->
                new ReglaPrestamo(
                        new BigDecimal("13.00"),
                        new BigDecimal("2000"),
                        new BigDecimal("100000"),
                        6,
                        48
                );
 
            default ->
                throw new IllegalArgumentException(
                        "Tipo de préstamo no válido."
                );
        };
    }
 
    private BigDecimal calcularCuotaMensual(
            BigDecimal monto,
            BigDecimal tasaAnual,
            int meses
    ) {
 
        double principal =
                monto.doubleValue();
 
        double tasaMensual =
                tasaAnual.doubleValue()
                / 100.0
                / 12.0;
 
        double cuota =
                principal
                * tasaMensual
                / (
                    1
                    - Math.pow(
                            1 + tasaMensual,
                            -meses
                    )
                );
 
        return BigDecimal
                .valueOf(cuota)
                .setScale(
                        2,
                        RoundingMode.HALF_UP
                );
    }
 
    private String limpiar(
            String valor
    ) {
 
        return valor == null
                ? ""
                : valor.trim();
    }
 
    private record ReglaPrestamo(
            BigDecimal tasaAnual,
            BigDecimal montoMinimo,
            BigDecimal montoMaximo,
            int plazoMinimo,
            int plazoMaximo
    ) {
    }
}