
package com.example.teecreatorbackend.dto;

public class AuthRequestDTO {

    private String correo;
    private String contrasena;

    public AuthRequestDTO() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
