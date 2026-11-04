
package com.example.teecreatorbackend.dto;

import java.time.LocalDateTime;

public class PedidoResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private LocalDateTime fecha;
    private String estado;

    // Constructor
    public PedidoResponseDTO(
            Long id,
            Long usuarioId,
            String nombreUsuario,
            LocalDateTime fecha,
            String estado) {

        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }
}
