package com.ifmd.mercaditolibre.services;
import com.ifmd.mercaditolibre.model.ProveedorEntity;
import com.ifmd.mercaditolibre.repository.ProveedorRepository;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import lombok.*;


@Service 
@RequiredArgsConstructor
public class ProveedorService {
    private final ProveedorRepository repository;

    //leer todos los registros
    @Transactional(readOnly = true)
    public List<ProveedorEntity> obtenerTodos(){
    return repository.findAll();
    }

    //obtener por id
    @Transactional(readOnly=true)
    public ProveedorEntity obtenerPorId(Long id){
        return repository.findById(id).
        orElseThrow(()-> new RuntimeException("Proveedores no encontrado: "));
    }

    //guardar un registro
    @Transactional
    public ProveedorEntity guardarProveedor(ProveedorEntity Proveedor){
        return repository.save(Proveedor);
        //aqui se guardan todos los Proveedors
    }

    //eliminar registro
    @Transactional
    public void eliminarProveedor(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("No se encontro el Proveedor");
        }
    repository.deleteById(id);
    }

    //actualizar registro
    @Transactional
    public ProveedorEntity actualizarProveedor(Long id, ProveedorEntity detalleProveedor){
        ProveedorEntity ProveedorExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Proveedor no existe !"));

        BeanUtils.copyProperties(detalleProveedor, ProveedorExistente, "id");
        return repository.save(ProveedorExistente);
    }
}
