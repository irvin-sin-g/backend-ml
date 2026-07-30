package com.ifmd.mercaditolibre.services;
import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.ifmd.mercaditolibre.model.CategoriaEntity;
import com.ifmd.mercaditolibre.repository.CategoriaRepository;


@Service 
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;

    //leer todos los registros
    @Transactional(readOnly = true)
    public List<CategoriaEntity> obtenerTodos(){
    return repository.findAll();
    }

    //obtener por id
    @Transactional(readOnly=true)
    public CategoriaEntity obtenerPorId(Long id){
        return repository.findById(id).
        orElseThrow(()-> new RuntimeException("Categoria no encontrada: "));
    }

    //guardar un registro
    @Transactional
    public CategoriaEntity guardarCategoria(CategoriaEntity Categoria){
        return repository.save(Categoria);
        //aqui se guardan todos los Categorias
    }

    //eliminar registro
    @Transactional
    public void eliminarCategoria(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("No se encontro la categoria");
        }
    repository.deleteById(id);
    }

    //actualizar registro
    @Transactional
    public CategoriaEntity actualizarCategoria(Long id, CategoriaEntity detalleCategoria){
        CategoriaEntity CategoriaExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Categoria no existe !"));

        BeanUtils.copyProperties(detalleCategoria, CategoriaExistente, "id");
        return repository.save(CategoriaExistente);
    }
}
