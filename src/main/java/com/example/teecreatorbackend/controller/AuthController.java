
package com.example.teecreatorbackend.controller;

import com.example.teecreatorbackend.dto.AuthRequestDTO;
import com.example.teecreatorbackend.dto.UsuarioResponseDTO;
import com.example.teecreatorbackend.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UsuarioService usuarioService;

    // Inyección de dependencias mediante constructor
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> iniciarSesion(
            @RequestBody AuthRequestDTO datos) {

        return usuarioService.autenticarUsuario(
                        datos.getCorreo(),
                        datos.getContrasena()
                )
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
