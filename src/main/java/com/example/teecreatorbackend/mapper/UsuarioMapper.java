
package com.example.teecreatorbackend.mapper;

import com.example.teecreatorbackend.dto.UsuarioRequestDTO;
import com.example.teecreatorbackend.dto.UsuarioResponseDTO;
import com.example.teecreatorbackend.model.Usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Convierte los datos recibidos del frontend en una entidad Usuario
    @Mapping(target = "id", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    // Convierte una entidad Usuario en un DTO de respuesta
    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
