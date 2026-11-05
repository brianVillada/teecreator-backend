package com.example.teecreatorbackend.service;

import com.example.teecreatorbackend.dto.CotizacionRequestDTO;
import com.example.teecreatorbackend.dto.CotizacionResponseDTO;

import java.util.List;

public interface CotizacionService {

    // Crear una nueva cotización
    CotizacionResponseDTO crearCotizacion(CotizacionRequestDTO request);

    // Consultar las cotizaciones de un usuario
    List<CotizacionResponseDTO> obtenerCotizacionesPorUsuario(Long usuarioId);
}