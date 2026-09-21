package main.java.org.sage.prestamos.creditos.model;
 
import java.math.BigDecimal;
import java.time.LocalDate;
 
public class Prestamos {
 
    private int idPrestamo;
    private int idUsuario;
 
    private String tipoPrestamo;
    private String telefono;
 
    private BigDecimal ingresoMensual;
    private BigDecimal monto;
 
    private LocalDate fecha;
 
    private int plazoMeses;
 
    private BigDecimal tasaInteres;
    private BigDecimal cuotaMensual;
 
    private String estado;
    private String motivo;
 
    public int getIdPrestamo() {
        return idPrestamo;
    }
 
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
 
    public int getIdUsuario() {
        return idUsuario;
    }
 
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
 
    public String getTipoPrestamo() {
        return tipoPrestamo;
    }
 
    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }
 
    public String getTelefono() {
        return telefono;
    }
 
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 
    public BigDecimal getIngresoMensual() {
        return ingresoMensual;
    }
 
    public void setIngresoMensual(BigDecimal ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }
 
    public BigDecimal getMonto() {
        return monto;
    }
 
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
 
    public LocalDate getFecha() {
        return fecha;
    }
 
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
 
    public int getPlazoMeses() {
        return plazoMeses;
    }
 
    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
 
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }
 
    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
 
    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }
 
    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }
 
    public String getEstado() {
        return estado;
    }
 
    public void setEstado(String estado) {
        this.estado = estado;
    }
 
    public String getMotivo() {
        return motivo;
    }
 
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}