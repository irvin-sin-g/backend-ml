package com.ifmd.mercaditolibre.controller;

import com.ifmd.mercaditolibre.model.VentasEntity;
import com.ifmd.mercaditolibre.services.VentasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class VentasController {

    private final VentasService servicio;

    @GetMapping("/")
    public ResponseEntity<List<VentasEntity>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasEntity> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminarVentas(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/procesar")
public ResponseEntity<?> procesarVenta(
        @RequestBody VentasEntity venta,
        Principal principal) {

    try {
        String emailCliente = principal.getName();
        VentasEntity nuevaVenta = servicio.procesarVenta(venta, emailCliente);
        return ResponseEntity.ok(nuevaVenta);
    } catch (Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}


    @GetMapping("/mis-compras")
    public ResponseEntity<List<VentasEntity>> listarMisCompras(Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok(servicio.obtenerVentasPorCliente(email));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<?> confirmarPago(@PathVariable Long id) {
        try {
            VentasEntity ventaPagada = servicio.confirmarPago(id);
            return ResponseEntity.ok(ventaPagada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentasEntity ventas) {
        try {
            VentasEntity ventasAct = servicio.actualizarVentas(id, ventas);
            return ResponseEntity.ok(ventasAct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
