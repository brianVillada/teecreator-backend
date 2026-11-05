
package com.example.teecreatorbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Nombre que identifica el diseño
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Lob
    @Column(name = "diseno_json", columnDefinition = "LONGTEXT")
    private String disenoJson;

    // Imagen PNG pequeña para mostrar en el historial
    @Lob
    @Column(name = "vista_previa", columnDefinition = "LONGTEXT")
    private String vistaPrevia;

    public Pedido() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
