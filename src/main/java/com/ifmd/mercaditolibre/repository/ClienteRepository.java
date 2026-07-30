package com.ifmd.mercaditolibre.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifmd.mercaditolibre.model.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{
    Optional<ClienteEntity> findByEmail(String email);
}
