package main.java.org.sage.prestamos.creditos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PrestamoAdminResponse {

    private final int idPrestamo;
    private final int idUsuario;

    private final String nombreCliente;
    private final String email;
    private final String telefono;

    private final String tipoPrestamo;

    private final BigDecimal ingresoMensual;
    private final BigDecimal monto;

    private final LocalDate fecha;

    private final int plazoMeses;

    private final BigDecimal tasaInteres;
    private final BigDecimal cuotaMensual;

    private final String estado;
    private final String motivo;

    private final String comentarioAdmin;

    private final LocalDateTime fechaResolucion;

    public PrestamoAdminResponse(
            int idPrestamo,
            int idUsuario,
            String nombreCliente,
            String email,
            String telefono,
            String tipoPrestamo,
            BigDecimal ingresoMensual,
            BigDecimal monto,
            LocalDate fecha,
            int plazoMeses,
            BigDecimal tasaInteres,
            BigDecimal cuotaMensual,
            String estado,
            String motivo,
            String comentarioAdmin,
            LocalDateTime fechaResolucion
    ) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.nombreCliente = nombreCliente;
        this.email = email;
        this.telefono = telefono;
        this.tipoPrestamo = tipoPrestamo;
        this.ingresoMensual = ingresoMensual;
        this.monto = monto;
        this.fecha = fecha;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.cuotaMensual = cuotaMensual;
        this.estado = estado;
        this.motivo = motivo;
        this.comentarioAdmin = comentarioAdmin;
        this.fechaResolucion = fechaResolucion;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public BigDecimal getIngresoMensual() {
        return ingresoMensual;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public String getEstado() {
        return estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getComentarioAdmin() {
        return comentarioAdmin;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }
}