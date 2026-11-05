
package com.example.teecreatorbackend.mapper;

import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.dto.PedidoResumenDTO;
import com.example.teecreatorbackend.model.Pedido;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    // Convierte un pedido en su respuesta completa
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    PedidoResponseDTO toResponseDTO(Pedido pedido);

    // Convierte un pedido en una respuesta resumida
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    PedidoResumenDTO toResumenDTO(Pedido pedido);
}
