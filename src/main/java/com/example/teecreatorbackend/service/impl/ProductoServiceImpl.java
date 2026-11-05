package com.example.teecreatorbackend.service.impl;

import com.example.teecreatorbackend.dto.ProductoResponseDTO;
import com.example.teecreatorbackend.mapper.ProductoMapper;
import com.example.teecreatorbackend.model.Producto;
import com.example.teecreatorbackend.repository.ProductoRepository;
import com.example.teecreatorbackend.service.ProductoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            ProductoMapper productoMapper
    ) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> obtenerProductosActivos() {

        List<Producto> productos = productoRepository.findByActivoTrue();

        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }
}