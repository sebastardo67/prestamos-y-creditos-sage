package main.java.org.sage.prestamos.creditos.dto.request;
 
import java.math.BigDecimal;
 
public class PrestamoRequest {
 
    private int idUsuario;
    private String tipoPrestamo;
    private String telefono;
    private BigDecimal ingresoMensual;
    private BigDecimal monto;
    private int plazoMeses;
    private String motivo;
 
    public PrestamoRequest(
            int idUsuario,
            String tipoPrestamo,
            String telefono,
            BigDecimal ingresoMensual,
            BigDecimal monto,
            int plazoMeses,
            String motivo
    ) {
        this.idUsuario = idUsuario;
        this.tipoPrestamo = tipoPrestamo;
        this.telefono = telefono;
        this.ingresoMensual = ingresoMensual;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.motivo = motivo;
    }
 
    public int getIdUsuario() {
        return idUsuario;
    }
 
    public String getTipoPrestamo() {
        return tipoPrestamo;
    }
 
    public String getTelefono() {
        return telefono;
    }
 
    public BigDecimal getIngresoMensual() {
        return ingresoMensual;
    }
 
    public BigDecimal getMonto() {
        return monto;
    }
 
    public int getPlazoMeses() {
        return plazoMeses;
    }
 
    public String getMotivo() {
        return motivo;
    }
}
