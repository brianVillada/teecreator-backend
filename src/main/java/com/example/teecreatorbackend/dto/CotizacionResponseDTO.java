package com.example.teecreatorbackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CotizacionResponseDTO {

    private Long id;

    private Long usuarioId;
    private Long pedidoId;
    private String nombreDiseno;

    private Long productoId;
    private String nombreProducto;

    private Integer cantidad;

    private BigDecimal anchoCm;
    private BigDecimal altoCm;
    private BigDecimal areaCm2;

    private BigDecimal precioCamiseta;
    private BigDecimal precioEstampacion;
    private BigDecimal precioTotal;

    private LocalDateTime fecha;

    public CotizacionResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getNombreDiseno() {
        return nombreDiseno;
    }

    public void setNombreDiseno(String nombreDiseno) {
        this.nombreDiseno = nombreDiseno;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getAnchoCm() {
        return anchoCm;
    }

    public void setAnchoCm(BigDecimal anchoCm) {
        this.anchoCm = anchoCm;
    }

    public BigDecimal getAltoCm() {
        return altoCm;
    }

    public void setAltoCm(BigDecimal altoCm) {
        this.altoCm = altoCm;
    }

    public BigDecimal getAreaCm2() {
        return areaCm2;
    }

    public void setAreaCm2(BigDecimal areaCm2) {
        this.areaCm2 = areaCm2;
    }

    public BigDecimal getPrecioCamiseta() {
        return precioCamiseta;
    }

    public void setPrecioCamiseta(BigDecimal precioCamiseta) {
        this.precioCamiseta = precioCamiseta;
    }

    public BigDecimal getPrecioEstampacion() {
        return precioEstampacion;
    }

    public void setPrecioEstampacion(BigDecimal precioEstampacion) {
        this.precioEstampacion = precioEstampacion;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}