
package com.example.teecreatorbackend.repository;

import com.example.teecreatorbackend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Consultar todos los pedidos de un usuario
    List<Pedido> findByUsuarioId(Long usuarioId);

}
