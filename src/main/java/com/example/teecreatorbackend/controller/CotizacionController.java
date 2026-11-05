package com.example.teecreatorbackend.controller;

import com.example.teecreatorbackend.dto.CotizacionRequestDTO;
import com.example.teecreatorbackend.dto.CotizacionResponseDTO;
import com.example.teecreatorbackend.service.CotizacionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    // Crear una cotización
    @PostMapping
    public ResponseEntity<CotizacionResponseDTO> crearCotizacion(
            @RequestBody CotizacionRequestDTO request
    ) {

        CotizacionResponseDTO cotizacion =
                cotizacionService.crearCotizacion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cotizacion);
    }

    // Consultar el historial de cotizaciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CotizacionResponseDTO>> obtenerCotizacionesPorUsuario(
            @PathVariable Long usuarioId
    ) {

        List<CotizacionResponseDTO> cotizaciones =
                cotizacionService.obtenerCotizacionesPorUsuario(usuarioId);

        return ResponseEntity.ok(cotizaciones);
    }
}