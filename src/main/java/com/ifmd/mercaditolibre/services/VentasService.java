package com.ifmd.mercaditolibre.services;

import com.ifmd.mercaditolibre.model.ClienteEntity;
import com.ifmd.mercaditolibre.model.DetalleVentaEntity;
import com.ifmd.mercaditolibre.model.ProductoEntity;
import com.ifmd.mercaditolibre.model.VentasEntity;
import com.ifmd.mercaditolibre.repository.ClienteRepository;
import com.ifmd.mercaditolibre.repository.ProductoRepository;
import com.ifmd.mercaditolibre.repository.VentasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasService {

    
    private final VentasRepository ventasRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public VentasEntity procesarVenta(VentasEntity ventaRequest, String email) {
        ClienteEntity cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no registrado: " + email));

        ventaRequest.setCliente(cliente);
        ventaRequest.setFecha(LocalDateTime.now());
        ventaRequest.setEstadoPago("PENDIENTE");

        double total = 0.0;
        for (DetalleVentaEntity detalle : ventaRequest.getDetalles()) {
            ProductoEntity producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));

            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente del producto");
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * detalle.getCantidad());
            detalle.setVentas(ventaRequest);

            total += detalle.getSubtotal();
        }
        ventaRequest.setTotal(total);
        

        return ventasRepository.save(ventaRequest);
    }

    @Transactional
    public List<VentasEntity> obtenerVentasPorCliente(String email) {
        return ventasRepository.findByClienteEmail(email);
    }

    @Transactional
    public VentasEntity confirmarPago(Long idVenta) {
        VentasEntity venta = ventasRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + idVenta));
        venta.setEstadoPago("Pagado");
        return ventasRepository.save(venta);
    }

    @Transactional(readOnly = true)
    public List<VentasEntity> obtenerTodos() {
        return ventasRepository.findAll();
    }

    @Transactional(readOnly = true)
    public VentasEntity obtenerPorId(Long id) {
        return ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }

    @Transactional
    public VentasEntity guardarVentas(VentasEntity ventas) {
        return ventasRepository.save(ventas);
    }

    @Transactional
    public void eliminarVentas(Long id) {
        if (!ventasRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la venta");
        }
        ventasRepository.deleteById(id);
    }

    @Transactional
    public VentasEntity actualizarVentas(Long id, VentasEntity detalleVentas) {
        VentasEntity ventasExistente = ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no existente!"));

        BeanUtils.copyProperties(detalleVentas, ventasExistente, "id");
        return ventasRepository.save(ventasExistente);
    }
}