package com.example.teecreatorbackend.service;

import com.example.teecreatorbackend.dto.PedidoRequestDTO;
import com.example.teecreatorbackend.dto.PedidoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    // Crear un pedido
    PedidoResponseDTO crearPedido(PedidoRequestDTO datos);

    // Consultar todos los pedidos
    List<PedidoResponseDTO> obtenerPedidos();

    // Consultar un pedido por su ID
    Optional<PedidoResponseDTO> obtenerPedidoPorId(Long id);

    // Consultar los pedidos de un usuario
    List<PedidoResponseDTO> obtenerPedidosPorUsuario(Long usuarioId);

    // Eliminar un pedido
    void eliminarPedido(Long id);
}