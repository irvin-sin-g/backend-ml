package com.ifmd.mercaditolibre.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmd.mercaditolibre.model.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    Optional<UsuarioEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
