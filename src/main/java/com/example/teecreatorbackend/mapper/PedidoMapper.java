
package com.example.teecreatorbackend.mapper;

import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    PedidoResponseDTO toResponseDTO(Pedido pedido);

}
