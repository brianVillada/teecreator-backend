
package com.example.teecreatorbackend.controller;
import com.example.teecreatorbackend.projection.UsuarioProjection;
import com.example.teecreatorbackend.dto.UsuarioRequestDTO;
import com.example.teecreatorbackend.dto.UsuarioResponseDTO;
import com.example.teecreatorbackend.mapper.UsuarioMapper;
import com.example.teecreatorbackend.model.Usuario;
import com.example.teecreatorbackend.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    // Inyección de dependencias mediante constructor
    public UsuarioController(
            UsuarioService usuarioService,
            UsuarioMapper usuarioMapper) {

        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    // Obtener usuarios utilizando una proyección
    @GetMapping("/proyeccion")
    public ResponseEntity<List<UsuarioProjection>> obtenerUsuariosProyectados() {

        List<UsuarioProjection> usuarios =
                usuarioService.obtenerUsuariosProyectados();

        return ResponseEntity.ok(usuarios);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {

        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
            @PathVariable Long id) {

        Optional<UsuarioResponseDTO> usuario =
                usuarioService.obtenerUsuarioPorId(id);

        return usuario
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener un usuario por su correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorCorreo(
            @PathVariable String correo) {

        Optional<UsuarioResponseDTO> usuario =
                usuarioService.obtenerUsuarioPorCorreo(correo);

        return usuario
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Registrar un usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO) {

        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);

        UsuarioResponseDTO usuarioGuardado =
                usuarioService.guardarUsuario(usuario);

        return ResponseEntity.ok(usuarioGuardado);
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
