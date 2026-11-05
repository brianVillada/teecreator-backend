
package com.example.teecreatorbackend.repository;

import com.example.teecreatorbackend.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    // Obtener las cotizaciones de un usuario
    List<Cotizacion> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    // Obtener las cotizaciones asociadas a un diseño
    List<Cotizacion> findByPedidoId(Long pedidoId);

}
