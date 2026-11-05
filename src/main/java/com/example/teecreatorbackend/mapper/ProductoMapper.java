
package com.example.teecreatorbackend.mapper;

import com.example.teecreatorbackend.dto.ProductoResponseDTO;
import com.example.teecreatorbackend.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    // Convierte una entidad Producto en ProductoResponseDTO
    public ProductoResponseDTO toResponseDTO(Producto producto) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }
}
