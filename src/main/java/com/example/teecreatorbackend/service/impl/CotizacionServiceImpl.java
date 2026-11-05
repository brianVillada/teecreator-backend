package com.example.teecreatorbackend.service.impl;

import com.example.teecreatorbackend.dto.CotizacionRequestDTO;
import com.example.teecreatorbackend.dto.CotizacionResponseDTO;

import com.example.teecreatorbackend.mapper.CotizacionMapper;

import com.example.teecreatorbackend.model.Cotizacion;
import com.example.teecreatorbackend.model.Pedido;
import com.example.teecreatorbackend.model.Producto;
import com.example.teecreatorbackend.model.Usuario;

import com.example.teecreatorbackend.repository.CotizacionRepository;
import com.example.teecreatorbackend.repository.PedidoRepository;
import com.example.teecreatorbackend.repository.ProductoRepository;
import com.example.teecreatorbackend.repository.UsuarioRepository;

import com.example.teecreatorbackend.service.CotizacionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CotizacionServiceImpl implements CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final CotizacionMapper cotizacionMapper;

    // Tarifa demostrativa DTF por centímetro cuadrado
    private static final BigDecimal TARIFA_POR_CM2 =
            new BigDecimal("10.00");

    // Valor mínimo de estampación por camiseta
    private static final BigDecimal PRECIO_MINIMO_ESTAMPACION =
            new BigDecimal("3000.00");

    public CotizacionServiceImpl(
            CotizacionRepository cotizacionRepository,
            UsuarioRepository usuarioRepository,
            PedidoRepository pedidoRepository,
            ProductoRepository productoRepository,
            CotizacionMapper cotizacionMapper
    ) {
        this.cotizacionRepository = cotizacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.cotizacionMapper = cotizacionMapper;
    }

    @Override
    @Transactional
    public CotizacionResponseDTO crearCotizacion(CotizacionRequestDTO request) {

        // 1. Validar los datos recibidos
        validarDatos(request);

        // 2. Buscar el usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() ->
                        new RuntimeException("El usuario no existe")
                );

        // 3. Buscar el diseño guardado
        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() ->
                        new RuntimeException("El diseño no existe")
                );

        // 4. Verificar que el diseño pertenezca al usuario
        if (!pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException(
                    "El diseño no pertenece al usuario indicado"
            );
        }

        // 5. Buscar el producto
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() ->
                        new RuntimeException("El producto no existe")
                );

        // 6. Verificar que el producto esté activo
        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new RuntimeException(
                    "El producto seleccionado no está disponible"
            );
        }

        // 7. Calcular el área de estampación
        BigDecimal area = request.getAnchoCm()
                .multiply(request.getAltoCm())
                .setScale(2, RoundingMode.HALF_UP);

        // 8. Calcular el precio de estampación por camiseta
        BigDecimal precioEstampacion = area
                .multiply(TARIFA_POR_CM2)
                .setScale(2, RoundingMode.HALF_UP);

        // 9. Aplicar el precio mínimo de estampación
        if (precioEstampacion.compareTo(PRECIO_MINIMO_ESTAMPACION) < 0) {
            precioEstampacion = PRECIO_MINIMO_ESTAMPACION;
        }

        // 10. Obtener el precio de la camiseta
        BigDecimal precioCamiseta = producto.getPrecio();

        // 11. Calcular el precio unitario
        BigDecimal precioUnitario = precioCamiseta
                .add(precioEstampacion);

        // 12. Calcular el total según la cantidad
        BigDecimal precioTotal = precioUnitario
                .multiply(BigDecimal.valueOf(request.getCantidad()))
                .setScale(2, RoundingMode.HALF_UP);

        // 13. Crear la entidad Cotizacion
        Cotizacion cotizacion = new Cotizacion();

        cotizacion.setUsuario(usuario);
        cotizacion.setPedido(pedido);
        cotizacion.setProducto(producto);

        cotizacion.setCantidad(request.getCantidad());

        cotizacion.setAnchoCm(request.getAnchoCm());
        cotizacion.setAltoCm(request.getAltoCm());
        cotizacion.setAreaCm2(area);

        cotizacion.setPrecioCamiseta(precioCamiseta);
        cotizacion.setPrecioEstampacion(precioEstampacion);
        cotizacion.setPrecioTotal(precioTotal);

        // 14. Guardar la cotización
        Cotizacion cotizacionGuardada =
                cotizacionRepository.save(cotizacion);

        // 15. Convertir la entidad a DTO y devolverla
        return cotizacionMapper.toResponseDTO(cotizacionGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CotizacionResponseDTO> obtenerCotizacionesPorUsuario(
            Long usuarioId
    ) {

        // Verificar que el usuario exista
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("El usuario no existe");
        }

        // Consultar las cotizaciones ordenadas de la más reciente a la más antigua
        List<Cotizacion> cotizaciones =
                cotizacionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);

        // Convertir las entidades a DTO
        return cotizaciones.stream()
                .map(cotizacionMapper::toResponseDTO)
                .toList();
    }

    // Validaciones básicas de la solicitud
    private void validarDatos(CotizacionRequestDTO request) {

        if (request == null) {
            throw new RuntimeException(
                    "La solicitud de cotización está vacía"
            );
        }

        if (request.getUsuarioId() == null
                || request.getPedidoId() == null
                || request.getProductoId() == null) {

            throw new RuntimeException(
                    "Debes indicar el usuario, el diseño y el producto"
            );
        }

        if (request.getCantidad() == null
                || request.getCantidad() < 1
                || request.getCantidad() > 100) {

            throw new RuntimeException(
                    "La cantidad debe estar entre 1 y 100 camisetas"
            );
        }

        if (request.getAnchoCm() == null
                || request.getAltoCm() == null
                || request.getAnchoCm().compareTo(BigDecimal.ZERO) <= 0
                || request.getAltoCm().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "El ancho y el alto deben ser mayores que cero"
            );
        }
    }
}