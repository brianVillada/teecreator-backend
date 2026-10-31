
package com.example.teecreatorbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Le indica a spring que esta clase manejara solicitudes HTTP
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "TeeCreator API funcionando correctamente";
    }
}
