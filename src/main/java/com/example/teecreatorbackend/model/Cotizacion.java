
package com.example.teecreatorbackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que realizó la cotización
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Diseño utilizado para calcular la cotización
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Camiseta seleccionada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "ancho_cm", nullable = false, precision = 8, scale = 2)
    private BigDecimal anchoCm;

    @Column(name = "alto_cm", nullable = false, precision = 8, scale = 2)
    private BigDecimal altoCm;

    @Column(name = "area_cm2", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaCm2;

    // Precio de una camiseta al momento de cotizar
    @Column(name = "precio_camiseta", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioCamiseta;

    // Precio de estampación de una unidad
    @Column(name = "precio_estampacion", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioEstampacion;

    // Precio total de todas las unidades
    @Column(name = "precio_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioTotal;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    // Constructor vacío requerido por JPA
    public Cotizacion() {
    }

    // Getters y Setters

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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
