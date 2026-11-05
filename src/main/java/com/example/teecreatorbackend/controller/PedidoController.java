
package com.example.teecreatorbackend.controller;

import com.example.teecreatorbackend.dto.PedidoRequestDTO;
import com.example.teecreatorbackend.dto.PedidoResponseDTO;
import com.example.teecreatorbackend.dto.PedidoResumenDTO;
import com.example.teecreatorbackend.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Crear un pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @RequestBody PedidoRequestDTO datos
    ) {
        PedidoResponseDTO pedido = pedidoService.crearPedido(datos);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedido);
    }

    // Consultar todos los pedidos resumidos
    @GetMapping
    public ResponseEntity<List<PedidoResumenDTO>> obtenerPedidos() {

        return ResponseEntity.ok(
                pedidoService.obtenerPedidos()
        );
    }

    // Consultar un pedido completo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidoPorId(
            @PathVariable Long id
    ) {
        return pedidoService.obtenerPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Consultar los pedidos resumidos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoResumenDTO>> obtenerPedidosPorUsuario(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                pedidoService.obtenerPedidosPorUsuario(usuarioId)
        );
    }

    // Actualizar el diseño y la vista previa de un pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizarPedido(
            @PathVariable Long id,
            @RequestBody PedidoRequestDTO datos
    ) {
        PedidoResponseDTO pedido = pedidoService.actualizarPedido(id, datos);

        return ResponseEntity.ok(pedido);
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
            @PathVariable Long id
    ) {
        pedidoService.eliminarPedido(id);

        return ResponseEntity.noContent().build();
    }
}
