
package com.example.teecreatorbackend.repository;

import com.example.teecreatorbackend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Obtener solamente los productos activos
    List<Producto> findByActivoTrue();

}
