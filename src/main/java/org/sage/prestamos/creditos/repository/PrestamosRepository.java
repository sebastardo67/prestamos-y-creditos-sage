package main.java.org.sage.prestamos.creditos.repository;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import java.util.ArrayList;
import java.util.List;
 
import main.java.org.sage.prestamos.creditos.config.ConnectionDb;
import main.java.org.sage.prestamos.creditos.model.Prestamos;
 
public class PrestamosRepository {
 
    public boolean guardar(Prestamos prestamos) {
 
        String sql = """
                INSERT INTO prestamos (
                    id_usuario,
                    tipo_prestamo,
                    telefono,
                    ingreso_mensual,
                    monto,
                    fecha,
                    plazo_meses,
                    tasa_interes,
                    cuota_mensual,
                    estado,
                    motivo
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
 
        try (
                Connection connection = ConnectionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
 
            statement.setInt(1, prestamos.getIdUsuario());
            statement.setString(2, prestamos.getTipoPrestamo());
            statement.setString(3, prestamos.getTelefono());
            statement.setBigDecimal(4, prestamos.getIngresoMensual());
            statement.setBigDecimal(5, prestamos.getMonto());
 
            statement.setDate(
                    6,
                    Date.valueOf(prestamos.getFecha())
            );
 
            statement.setInt(7, prestamos.getPlazoMeses());
            statement.setBigDecimal(8, prestamos.getTasaInteres());
            statement.setBigDecimal(9, prestamos.getCuotaMensual());
            statement.setString(10, prestamos.getEstado());
            statement.setString(11, prestamos.getMotivo());
 
            return statement.executeUpdate() > 0;
 
        } catch (SQLException e) {
 
            throw new IllegalStateException(
                    "No se pudo registrar la solicitud del préstamo.",
                    e
            );
        }
    }
 
    public List<Prestamos> listarPorUsuario(int idUsuario) {
 
        List<Prestamos> prestamos = new ArrayList<>();
 
        String sql = """
                SELECT
                    id_prestamo,
                    id_usuario,
                    tipo_prestamo,
                    telefono,
                    ingreso_mensual,
                    monto,
                    fecha,
                    plazo_meses,
                    tasa_interes,
                    cuota_mensual,
                    estado,
                    motivo
                FROM prestamos
                WHERE id_usuario = ?
                ORDER BY fecha DESC, id_prestamo DESC
                """;
 
        try (
                Connection connection = ConnectionDb.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
 
            statement.setInt(1, idUsuario);
 
            try (ResultSet rs = statement.executeQuery()) {
 
                while (rs.next()) {
 
                    Prestamos prestamo = new Prestamos();
 
                    prestamo.setIdPrestamo(
                            rs.getInt("id_prestamo")
                    );
 
                    prestamo.setIdUsuario(
                            rs.getInt("id_usuario")
                    );
 
                    prestamo.setTipoPrestamo(
                            rs.getString("tipo_prestamo")
                    );
 
                    prestamo.setTelefono(
                            rs.getString("telefono")
                    );
 
                    prestamo.setIngresoMensual(
                            rs.getBigDecimal("ingreso_mensual")
                    );
 
                    prestamo.setMonto(
                            rs.getBigDecimal("monto")
                    );
 
                    prestamo.setFecha(
                            rs.getDate("fecha").toLocalDate()
                    );
 
                    prestamo.setPlazoMeses(
                            rs.getInt("plazo_meses")
                    );
 
                    prestamo.setTasaInteres(
                            rs.getBigDecimal("tasa_interes")
                    );
 
                    prestamo.setCuotaMensual(
                            rs.getBigDecimal("cuota_mensual")
                    );
 
                    prestamo.setEstado(
                            rs.getString("estado")
                    );
 
                    prestamo.setMotivo(
                            rs.getString("motivo")
                    );
 
                    prestamos.add(prestamo);
                }
 
            }
 
        } catch (SQLException e) {
 
            throw new IllegalStateException(
                    "No se pudieron consultar los préstamos.",
                    e
            );
        }
 
        return prestamos;
    }
}