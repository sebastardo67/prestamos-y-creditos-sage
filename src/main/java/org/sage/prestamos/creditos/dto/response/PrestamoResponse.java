package main.java.org.sage.prestamos.creditos.dto.response;
 
import java.math.BigDecimal;
import java.time.LocalDate;
 
public class PrestamoResponse {
 
    private int idPrestamo;
    private String tipoPrestamo;
    private BigDecimal monto;
    private LocalDate fecha;
    private int plazoMeses;
    private BigDecimal tasaInteres;
    private BigDecimal cuotaMensual;
    private String estado;
 
    public PrestamoResponse(
            int idPrestamo,
            String tipoPrestamo,
            BigDecimal monto,
            LocalDate fecha,
            int plazoMeses,
            BigDecimal tasaInteres,
            BigDecimal cuotaMensual,
            String estado
    ) {
        this.idPrestamo = idPrestamo;
        this.tipoPrestamo = tipoPrestamo;
        this.monto = monto;
        this.fecha = fecha;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.cuotaMensual = cuotaMensual;
        this.estado = estado;
    }
 
    public int getIdPrestamo() {
        return idPrestamo;
    }
 
    public String getTipoPrestamo() {
        return tipoPrestamo;
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
}