package com.example.teecreatorbackend.service;

import com.example.teecreatorbackend.dto.UsuarioResponseDTO;
import com.example.teecreatorbackend.model.Usuario;
import com.example.teecreatorbackend.projection.UsuarioProjection;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    UsuarioResponseDTO guardarUsuario(Usuario usuario);

    List<UsuarioResponseDTO> obtenerUsuarios();

    Optional<UsuarioResponseDTO> obtenerUsuarioPorId(Long id);

    Optional<UsuarioResponseDTO> obtenerUsuarioPorCorreo(String correo);

    // Validar las credenciales de un usuario
    Optional<UsuarioResponseDTO> autenticarUsuario(
            String correo,
            String contrasena
    );

    void eliminarUsuario(Long id);

    // Obtener usuarios utilizando una proyección
    List<UsuarioProjection> obtenerUsuariosProyectados();
}