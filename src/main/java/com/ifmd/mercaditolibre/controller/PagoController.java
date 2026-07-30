package com.ifmd.mercaditolibre.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import com.ifmd.mercaditolibre.dto.PagoRequest;
import com.ifmd.mercaditolibre.model.VentasEntity;
import com.ifmd.mercaditolibre.services.VentasService;

@RestController
@RequestMapping("/api/v1/pagos")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PagoController {

    @Value("${stripe.apikey.secret}")
    private String stripeSecretKey;

    private final VentasService ventaService;

    public PagoController(VentasService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/crear-intencion")
    public ResponseEntity<?> crearIntencion(@RequestBody PagoRequest peticion) {
        if (peticion == null || peticion.getIdVenta() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "El idVenta es obligatorio"));
        }

        try {
            Stripe.apiKey = stripeSecretKey;

            VentasEntity venta = ventaService.obtenerPorId(peticion.getIdVenta());
            if (venta == null) {
                return ResponseEntity.status(404).body(Map.of("message", "No se encontró la venta"));
            }

            if (venta.getTotal() == null || venta.getTotal() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("message", "El total de la venta es inválido"));
            }

            long montoCentavos = Math.round(venta.getTotal() * 100);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(montoCentavos)
                    .setCurrency(peticion.getMoneda() != null ? peticion.getMoneda().toLowerCase() : "mxn")
                    .putMetadata("id_venta", venta.getId().toString())
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            return ResponseEntity.ok(Map.of("clientSecret", intent.getClientSecret()));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/confirmar-pago/{idVenta}")
    public ResponseEntity<?> confirmarPago(@PathVariable Long idVenta) {
        if (idVenta == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "El idVenta no puede ser null"));
        }

        try {
            VentasEntity ventaActualizada = ventaService.confirmarPago(idVenta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }
}
