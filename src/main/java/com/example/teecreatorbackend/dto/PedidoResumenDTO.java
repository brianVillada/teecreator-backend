
package com.example.teecreatorbackend.dto;

import java.time.LocalDateTime;

public class PedidoResumenDTO {

    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private String nombre;
    private LocalDateTime fecha;
    private String estado;
    private String vistaPrevia;

    public PedidoResumenDTO(
            Long id,
            Long usuarioId,
            String nombreUsuario,
            String nombre,
            LocalDateTime fecha,
            String estado,
            String vistaPrevia
    ) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
        this.vistaPrevia = vistaPrevia;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getVistaPrevia() {
        return vistaPrevia;
    }
}
