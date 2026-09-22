package main.java.org.sage.prestamos.creditos.dto.response;

import java.math.BigDecimal;

public class AdminResumeResponse {

    private final int totalSolicitudes;
    private final int pendientes;
    private final int aprobados;
    private final int rechazados;
    private final BigDecimal montoTotalAprobado;

    public AdminResumeResponse(
            int totalSolicitudes,
            int pendientes,
            int aprobados,
            int rechazados,
            BigDecimal montoTotalAprobado
    ) {
        this.totalSolicitudes = totalSolicitudes;
        this.pendientes = pendientes;
        this.aprobados = aprobados;
        this.rechazados = rechazados;
        this.montoTotalAprobado = montoTotalAprobado;
    }

    public int getTotalSolicitudes() {
        return totalSolicitudes;
    }

    public int getPendientes() {
        return pendientes;
    }

    public int getAprobados() {
        return aprobados;
    }

    public int getRechazados() {
        return rechazados;
    }

    public BigDecimal getMontoTotalAprobado() {
        return montoTotalAprobado;
    }
}