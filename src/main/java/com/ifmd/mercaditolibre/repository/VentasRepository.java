package com.ifmd.mercaditolibre.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifmd.mercaditolibre.model.VentasEntity;

@Repository
public interface VentasRepository extends JpaRepository<VentasEntity, Long>{
    // Busca las ventas asociadas al email del cliente
    List<VentasEntity> findByClienteEmail(String email);
}