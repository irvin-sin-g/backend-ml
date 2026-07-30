package com.ifmd.mercaditolibre.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "ventas")

public class VentasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.time.LocalDateTime fecha;
    private Double total;
    private String estadoPago;

    //----- relaciones ------
    @ManyToOne 
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "ventas", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleVentaEntity> detalles = new ArrayList<>();
}