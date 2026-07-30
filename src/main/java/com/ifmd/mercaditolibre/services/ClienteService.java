package com.ifmd.mercaditolibre.services;
import javax.management.RuntimeErrorException;
import lombok.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.ifmd.mercaditolibre.model.ClienteEntity;
import com.ifmd.mercaditolibre.repository.ClienteRepository;


@Service 
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    //leer todos los registros
    @Transactional(readOnly = true)
    public List<ClienteEntity> obtenerTodos(){
    return repository.findAll();
    }

    //obtener por id
    @Transactional(readOnly=true)
    public ClienteEntity obtenerPorId(Long id){
        return repository.findById(id).
        orElseThrow(()-> new RuntimeException("Clientes no encontrado: "));
    }

    //guardar un registro
    @Transactional
    public ClienteEntity guardarCliente(ClienteEntity Cliente){
        return repository.save(Cliente);
        //aqui se guardan todos los Clientes
    }

    //eliminar registro
    @Transactional
    public void eliminarCliente(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("No se encontro el Cliente");
        }
    repository.deleteById(id);
    }

    //actualizar registro
    @Transactional
    public ClienteEntity actualizarCliente(Long id, ClienteEntity detalleCliente){
        ClienteEntity ClienteExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Cliente no existe !"));

        BeanUtils.copyProperties(detalleCliente, ClienteExistente, "id");
        return repository.save(ClienteExistente);
    }
}