
package com.example.teecreatorbackend.mapper;

import com.example.teecreatorbackend.dto.CotizacionResponseDTO;
import com.example.teecreatorbackend.model.Cotizacion;
import org.springframework.stereotype.Component;

@Component
public class CotizacionMapper {

    // Convierte una entidad Cotizacion en CotizacionResponseDTO
    public CotizacionResponseDTO toResponseDTO(Cotizacion cotizacion) {

        CotizacionResponseDTO dto = new CotizacionResponseDTO();

        dto.setId(cotizacion.getId());

        // Información del usuario
        dto.setUsuarioId(cotizacion.getUsuario().getId());

        // Información del diseño
        dto.setPedidoId(cotizacion.getPedido().getId());
        dto.setNombreDiseno(cotizacion.getPedido().getNombre());

        // Información del producto
        dto.setProductoId(cotizacion.getProducto().getId());
        dto.setNombreProducto(cotizacion.getProducto().getNombre());

        // Información de la cotización
        dto.setCantidad(cotizacion.getCantidad());

        dto.setAnchoCm(cotizacion.getAnchoCm());
        dto.setAltoCm(cotizacion.getAltoCm());
        dto.setAreaCm2(cotizacion.getAreaCm2());

        dto.setPrecioCamiseta(cotizacion.getPrecioCamiseta());
        dto.setPrecioEstampacion(cotizacion.getPrecioEstampacion());
        dto.setPrecioTotal(cotizacion.getPrecioTotal());

        dto.setFecha(cotizacion.getFecha());

        return dto;
    }
}
