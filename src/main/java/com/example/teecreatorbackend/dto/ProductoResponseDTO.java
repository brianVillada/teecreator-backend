package com.example.teecreatorbackend.dto;

import java.math.BigDecimal;

public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private BigDecimal precio;

    public ProductoResponseDTO() {
    }

    public ProductoResponseDTO(Long id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}