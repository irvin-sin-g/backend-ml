package com.ifmd.mercaditolibre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifmd.mercaditolibre.model.DetalleVentaEntity;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long>{

}
