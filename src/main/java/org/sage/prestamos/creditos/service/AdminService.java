package main.java.org.sage.prestamos.creditos.service;

import java.math.BigDecimal;
import java.util.List;
import main.java.org.sage.prestamos.creditos.dto.response.AdminResumeResponse;

import main.java.org.sage.prestamos.creditos.dto.response.AdminResumeResponse;
import main.java.org.sage.prestamos.creditos.dto.response.PrestamoAdminResponse;
import main.java.org.sage.prestamos.creditos.dto.response.UsuarioAdminResponse;
import main.java.org.sage.prestamos.creditos.model.Usuarios;
import main.java.org.sage.prestamos.creditos.repository.PrestamosRepository;
import main.java.org.sage.prestamos.creditos.repository.UsuariosRepository;

public class AdminService {

    private final PrestamosRepository prestamosRepository;
    private final UsuariosRepository usuariosRepository;

    public AdminService() {

        this.prestamosRepository =
                new PrestamosRepository();

        this.usuariosRepository =
                new UsuariosRepository();
    }


    public AdminResumeResponse obtenerResumen(
            int idAdministrador
    ) {

        validarAdministrador(
                idAdministrador
        );

        List<PrestamoAdminResponse> solicitudes =
                prestamosRepository
                        .listarTodosConUsuario(
                                "TODOS"
                        );

        int pendientes = 0;
        int aprobados = 0;
        int rechazados = 0;

        BigDecimal montoAprobado =
                BigDecimal.ZERO;

        for (PrestamoAdminResponse p :
                solicitudes) {

            if ("PENDIENTE".equalsIgnoreCase(
                    p.getEstado())) {

                pendientes++;

            } else if ("APROBADO".equalsIgnoreCase(
                    p.getEstado())) {

                aprobados++;

                montoAprobado =
                        montoAprobado.add(
                                p.getMonto()
                        );

            } else if ("RECHAZADO".equalsIgnoreCase(
                    p.getEstado())) {

                rechazados++;
            }
        }

        return new AdminResumeResponse(
                solicitudes.size(),
                pendientes,
                aprobados,
                rechazados,
                montoAprobado
        );
    }


    public List<PrestamoAdminResponse>
            listarSolicitudes(
                    int idAdministrador,
                    String estado
            ) {

        validarAdministrador(
                idAdministrador
        );

        return prestamosRepository
                .listarTodosConUsuario(
                        estado
                );
    }


    public void aprobarPrestamo(
            int idAdministrador,
            int idPrestamo
    ) {

        validarAdministrador(
                idAdministrador
        );

        boolean actualizado =
                prestamosRepository
                        .resolverPrestamo(
                                idPrestamo,
                                idAdministrador,
                                "APROBADO",
                                "Solicitud aprobada."
                        );

        if (!actualizado) {

            throw new IllegalStateException(
                    "El préstamo ya fue resuelto o no existe."
            );
        }
    }


    public void rechazarPrestamo(
            int idAdministrador,
            int idPrestamo,
            String motivo
    ) {

        validarAdministrador(
                idAdministrador
        );

        if (motivo == null
                || motivo.isBlank()) {

            throw new IllegalArgumentException(
                    "Debe indicar el motivo del rechazo."
            );
        }

        boolean actualizado =
                prestamosRepository
                        .resolverPrestamo(
                                idPrestamo,
                                idAdministrador,
                                "RECHAZADO",
                                motivo.trim()
                        );

        if (!actualizado) {

            throw new IllegalStateException(
                    "El préstamo ya fue resuelto o no existe."
            );
        }
    }


    public List<UsuarioAdminResponse>
            listarClientes(
                    int idAdministrador
            ) {

        validarAdministrador(
                idAdministrador
        );

        return usuariosRepository
                .listarClientes()
                .stream()
                .map(usuario ->
                        new UsuarioAdminResponse(
                                usuario.getIdUsuario(),
                                usuario.getNombre()
                                + " "
                                + usuario.getApellido(),
                                usuario.getUsername(),
                                usuario.getEmail(),
                                usuario.isActivo()
                        )
                )
                .toList();
    }


    public void cambiarEstadoCliente(
            int idAdministrador,
            int idCliente,
            boolean activo
    ) {

        validarAdministrador(
                idAdministrador
        );

        boolean actualizado =
                usuariosRepository
                        .actualizarEstadoCliente(
                                idCliente,
                                activo
                        );

        if (!actualizado) {

            throw new IllegalStateException(
                    "No se pudo cambiar el estado del usuario."
            );
        }
    }


    private void validarAdministrador(
            int idAdministrador
    ) {

        Usuarios administrador =
                usuariosRepository
                        .buscarPorId(
                                idAdministrador
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Administrador no encontrado."
                                )
                        );

        if (!administrador.isActivo()) {

            throw new IllegalStateException(
                    "La cuenta administrativa está inactiva."
            );
        }

        if (administrador.getIdRol() != 1) {

            throw new SecurityException(
                    "El usuario no tiene permisos administrativos."
            );
        }
    }
}