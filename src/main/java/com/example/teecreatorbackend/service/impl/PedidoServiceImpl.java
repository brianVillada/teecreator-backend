
package com.example.teecreatorbackend.service.impl;

import com.example.teecreatorbackend.dto.PedidoRequestDTO;
import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.dto.PedidoResumenDTO;

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

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            PedidoMapper pedidoMapper
    ) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoMapper = pedidoMapper;
    }

    // Validar el nombre recibido desde el frontend
    private String validarNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes asignar un nombre al diseño."
            );
        }

        String nombreLimpio = nombre.trim();

        if (nombreLimpio.length() > 120) {
            throw new IllegalArgumentException(
                    "El nombre del diseño no puede superar los 120 caracteres."
            );
        }

        return nombreLimpio;
    }

    // Crear un pedido con su diseño
    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO datos) {

        if (datos.getUsuarioId() == null) {
            throw new IllegalArgumentException(
                    "Es necesario identificar al usuario."
            );
        }

        String nombre = validarNombre(datos.getNombre());

        if (datos.getDisenoJson() == null || datos.getDisenoJson().isBlank()) {
            throw new IllegalArgumentException(
                    "El diseño no puede estar vacío."
            );
        }

        if (datos.getVistaPrevia() == null || datos.getVistaPrevia().isBlank()) {
            throw new IllegalArgumentException(
                    "La vista previa no puede estar vacía."
            );
        }

        Usuario usuario = usuarioRepository.findById(datos.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un usuario con ID: " + datos.getUsuarioId()
                ));

        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.setNombre(nombre);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("Pendiente");
        pedido.setDisenoJson(datos.getDisenoJson());
        pedido.setVistaPrevia(datos.getVistaPrevia());

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponseDTO(pedidoGuardado);
    }

    // Consultar todos los pedidos sin incluir sus diseños
    @Override
    public List<PedidoResumenDTO> obtenerPedidos() {

        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResumenDTO)
                .toList();
    }

    // Consultar un pedido individual con su diseño completo
    @Override
    public Optional<PedidoResponseDTO> obtenerPedidoPorId(Long id) {

        return pedidoRepository.findById(id)
                .map(pedidoMapper::toResponseDTO);
    }

    // Consultar los pedidos de un usuario sin descargar sus diseños
    @Override
    public List<PedidoResumenDTO> obtenerPedidosPorUsuario(Long usuarioId) {

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(pedidoMapper::toResumenDTO)
                .toList();
    }

    // Actualizar el diseño de un pedido existente
    @Override
    @Transactional
    public PedidoResponseDTO actualizarPedido(
            Long id,
            PedidoRequestDTO datos
    ) {

        // Buscar el pedido que queremos actualizar
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un pedido con ID: " + id
                ));

        // Verificar que se haya enviado el usuario
        if (datos.getUsuarioId() == null) {
            throw new IllegalArgumentException(
                    "Es necesario identificar al usuario."
            );
        }

        // Verificar que el pedido pertenezca al usuario indicado
        if (!pedido.getUsuario().getId().equals(datos.getUsuarioId())) {
            throw new IllegalArgumentException(
                    "No tienes autorización para modificar este pedido."
            );
        }

        // Validar el nombre nuevo
        String nombre = validarNombre(datos.getNombre());

        // Verificar que se hayan enviado los datos del diseño
        if (datos.getDisenoJson() == null || datos.getDisenoJson().isBlank()) {
            throw new IllegalArgumentException(
                    "El diseño no puede estar vacío."
            );
        }

        if (datos.getVistaPrevia() == null || datos.getVistaPrevia().isBlank()) {
            throw new IllegalArgumentException(
                    "La vista previa no puede estar vacía."
            );
        }

        // Actualizar los datos del diseño
        pedido.setNombre(nombre);
        pedido.setDisenoJson(datos.getDisenoJson());
        pedido.setVistaPrevia(datos.getVistaPrevia());

        // Guardar los cambios del pedido existente
        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponseDTO(pedidoActualizado);
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
