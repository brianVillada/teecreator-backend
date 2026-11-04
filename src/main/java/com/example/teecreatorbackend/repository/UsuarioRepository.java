
package com.example.teecreatorbackend.repository;

import com.example.teecreatorbackend.model.Usuario;
import com.example.teecreatorbackend.projection.UsuarioProjection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario mediante su correo electrónico
    Optional<Usuario> findByCorreo(String correo);

    // Obtener todos los usuarios mediante una proyección
    List<UsuarioProjection> findAllProjectedBy();
}
