
package com.example.teecreatorbackend.service.impl;

import com.example.teecreatorbackend.dto.UsuarioResponseDTO;
import com.example.teecreatorbackend.mapper.UsuarioMapper;
import com.example.teecreatorbackend.model.Usuario;
import com.example.teecreatorbackend.projection.UsuarioProjection;
import com.example.teecreatorbackend.repository.UsuarioRepository;
import com.example.teecreatorbackend.service.UsuarioService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias mediante constructor
    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Guardar un usuario con su contraseña protegida mediante BCrypt
    @Override
    public UsuarioResponseDTO guardarUsuario(Usuario usuario) {

        usuario.setContrasena(
                passwordEncoder.encode(usuario.getContrasena())
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(usuarioGuardado);
    }

    // Validar las credenciales de un usuario
    @Override
    public Optional<UsuarioResponseDTO> autenticarUsuario(
            String correo,
            String contrasena) {

        return usuarioRepository.findByCorreo(correo)
                .filter(usuario ->
                        passwordEncoder.matches(
                                contrasena,
                                usuario.getContrasena()
                        )
                )
                .map(usuarioMapper::toResponseDTO);
    }

    // Obtener todos los usuarios
    @Override
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    // Obtener un usuario por su ID
    @Override
    public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponseDTO);
    }

    // Obtener un usuario por su correo
    @Override
    public Optional<UsuarioResponseDTO> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(usuarioMapper::toResponseDTO);
    }

    // Obtener usuarios mediante una proyección
    @Override
    public List<UsuarioProjection> obtenerUsuariosProyectados() {
        return usuarioRepository.findAllProjectedBy();
    }

    // Eliminar un usuario por su ID
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
