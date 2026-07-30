package com.ifmd.mercaditolibre.model;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="detalleventa")

public class DetalleVentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    //----relaciones----
    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonBackReference
    private VentasEntity ventas;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;
}
