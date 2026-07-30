package com.ifmd.mercaditolibre.services;

import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.ifmd.mercaditolibre.model.DetalleVentaEntity;
import com.ifmd.mercaditolibre.repository.DetalleVentaRepository;


@Service 
@RequiredArgsConstructor
public class DetalleVentaService {
    private final DetalleVentaRepository repository;

    //leer todos los registros
    @Transactional(readOnly = true)
    public List<DetalleVentaEntity> obtenerTodos(){
    return repository.findAll();
    }

    //obtener por id
    @Transactional(readOnly=true)
    public DetalleVentaEntity obtenerPorId(Long id){
        return repository.findById(id).
        orElseThrow(()-> new RuntimeException("Detalle de venta no encontrado: "));
    }

    //guardar un registro
    @Transactional
    public DetalleVentaEntity guardarDetalleVenta(DetalleVentaEntity DetalleVenta){
        return repository.save(DetalleVenta);
        //aqui se guardan todos los DetalleVentas
    }

    //eliminar registro
    @Transactional
    public void eliminarDetalleVenta(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("No se encontro el detalle de venta");
        }
    repository.deleteById(id);
    }

    //actualizar registro
    @Transactional
    public DetalleVentaEntity actualizarDetalleVenta(Long id, DetalleVentaEntity detalleDetalleVenta){
        DetalleVentaEntity DetalleVentaExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Detalle de venta no existe !"));

        BeanUtils.copyProperties(detalleDetalleVenta, DetalleVentaExistente, "id");
        return repository.save(DetalleVentaExistente);
    }
}