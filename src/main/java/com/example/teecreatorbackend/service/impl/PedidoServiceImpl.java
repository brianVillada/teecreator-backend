package com.example.teecreatorbackend.service.impl;

import com.example.teecreatorbackend.dto.PedidoRequestDTO;
import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.mapper.PedidoMapper;
import com.example.teecreatorbackend.model.Pedido;
import com.example.teecreatorbackend.model.Usuario;
import com.example.teecreatorbackend.repository.PedidoRepository;
import com.example.teecreatorbackend.repository.UsuarioRepository;
import com.example.teecreatorbackend.service.PedidoService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMapper pedidoMapper;

    // Inyección de dependencias mediante constructor
    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            PedidoMapper pedidoMapper) {

        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoMapper = pedidoMapper;
    }

    // Crear un pedido
    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO datos) {

        // Buscar el usuario que está realizando el pedido
        Usuario usuario = usuarioRepository.findById(datos.getUsuarioId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "No existe un usuario con ID: " + datos.getUsuarioId()
                        )
                );

        // Crear la entidad Pedido
        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("Pendiente");

        // Guardar el pedido en MySQL
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Convertir la entidad en DTO y devolverla
        return pedidoMapper.toResponseDTO(pedidoGuardado);
    }

    // Consultar todos los pedidos
    @Override
    public List<PedidoResponseDTO> obtenerPedidos() {

        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    // Consultar un pedido por su ID
    @Override
    public Optional<PedidoResponseDTO> obtenerPedidoPorId(Long id) {

        return pedidoRepository.findById(id)
                .map(pedidoMapper::toResponseDTO);
    }

    // Consultar los pedidos de un usuario
    @Override
    public List<PedidoResponseDTO> obtenerPedidosPorUsuario(Long usuarioId) {

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .toList();
    }

    // Eliminar un pedido
    @Override
    @Transactional
    public void eliminarPedido(Long id) {

        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "No existe un pedido con ID: " + id
            );
        }

        pedidoRepository.deleteById(id);
    }
}