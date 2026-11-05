package com.example.teecreatorbackend.controller;

import com.example.teecreatorbackend.dto.ProductoResponseDTO;
import com.example.teecreatorbackend.service.ProductoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Consultar los productos disponibles
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosActivos() {

        List<ProductoResponseDTO> productos =
                productoService.obtenerProductosActivos();

        return ResponseEntity.ok(productos);
    }
}