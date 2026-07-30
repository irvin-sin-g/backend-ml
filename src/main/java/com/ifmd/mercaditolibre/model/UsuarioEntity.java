package com.ifmd.mercaditolibre.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")

public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        unique = true,
        nullable = false,
        length = 150
    )
    private String username;

    @Column(
        nullable = false,
        length = 255
    )
    private String password;

    @Column(
        nullable = false,
        length = 150
    )
    private String nombre;

    @Column(
        length = 10
    )
    private String telefono;

    @Column(
        length = 255
    )
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 30
    )
    private Rol role;
}