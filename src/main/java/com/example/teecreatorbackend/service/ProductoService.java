package com.example.teecreatorbackend.service;

import com.example.teecreatorbackend.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    // Obtener los productos que están disponibles para cotizar
    List<ProductoResponseDTO> obtenerProductosActivos();
}