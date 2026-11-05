
package com.example.teecreatorbackend.service;

import com.example.teecreatorbackend.dto.PedidoRequestDTO;
import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.dto.PedidoResumenDTO;

import java.util.List;
import java.util.Optional;

public interface PedidoService {


    PedidoResponseDTO actualizarPedido(
            Long id,
            PedidoRequestDTO datos
    );

    PedidoResponseDTO crearPedido(PedidoRequestDTO datos);

    List<PedidoResumenDTO> obtenerPedidos();

    Optional<PedidoResponseDTO> obtenerPedidoPorId(Long id);

    List<PedidoResumenDTO> obtenerPedidosPorUsuario(Long usuarioId);

    void eliminarPedido(Long id);
}
