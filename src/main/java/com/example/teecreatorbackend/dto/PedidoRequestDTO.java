
package com.example.teecreatorbackend.dto;

public class PedidoRequestDTO {

    private Long usuarioId;
    private String nombre;
    private String disenoJson;
    private String vistaPrevia;

    public PedidoRequestDTO() {}

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDisenoJson() {
        return disenoJson;
    }

    public void setDisenoJson(String disenoJson) {
        this.disenoJson = disenoJson;
    }

    public String getVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(String vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }
}
