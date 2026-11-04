
package com.example.teecreatorbackend.projection;

// Proyección para consultar únicamente los datos necesarios del usuario
public interface UsuarioProjection {

    Long getId();

    String getNombre();

    String getCorreo();
}
