/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.org.sage.prestamos.creditos.model;

import java.time.LocalDate;

public class Prestamos {

    private int idPrestamo;
    private int idUsuarios;
    private double monto;
    private LocalDate fecha;
    private int plazoMeses;
    private double tasaInteres;
    private double cuotaMensual;
    private String estado;
    private String motivo;

    public Prestamos() {
    }

    public Prestamos(int idPrestamo, int idUsuarios, double monto, LocalDate fecha,
                     int plazoMeses, double tasaInteres, double cuotaMensual,
                     String estado, String motivo) {
        this.idPrestamo = idPrestamo;
        this.idUsuarios = idUsuarios;
        this.monto = monto;
        this.fecha = fecha;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.cuotaMensual = cuotaMensual;
        this.estado = estado;
        this.motivo = motivo;
    }

    public Prestamos(int idUsuarios, double monto, LocalDate fecha,
                     int plazoMeses, double tasaInteres, double cuotaMensual,
                     String estado, String motivo) {
        this.idUsuarios = idUsuarios;
        this.monto = monto;
        this.fecha = fecha;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.cuotaMensual = cuotaMensual;
        this.estado = estado;
        this.motivo = motivo;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
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

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
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