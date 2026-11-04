
package com.example.teecreatorbackend.dto;

public class PedidoRequestDTO {

    private Long usuarioId;

    // Constructor vacío
    public PedidoRequestDTO() {
    }

    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
