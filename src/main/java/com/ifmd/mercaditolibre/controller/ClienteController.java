package com.ifmd.mercaditolibre.controller;

import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.ifmd.mercaditolibre.model.ClienteEntity;
import com.ifmd.mercaditolibre.repository.ClienteRepository;
import com.ifmd.mercaditolibre.services.ClienteService;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes") //mapeo general Clientes
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequiredArgsConstructor

public class ClienteController {
    private final ClienteService servicio;

    //endpoint ver todos los Clientes

    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>>listar(){
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    //consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> obtenerDetalles(@PathVariable Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id)); //200
    }

    //eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteEntity> eliminar(@PathVariable Long id){
        servicio.eliminarCliente(id);
        return ResponseEntity.noContent().build(); //204 no content
    }

    //agregar
    @PostMapping
    public ResponseEntity<ClienteEntity> crear(@RequestBody ClienteEntity Cliente){
        ClienteEntity nuevo = servicio.guardarCliente(Cliente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @RequestBody ClienteEntity Cliente){
        try{
        ClienteEntity ClienteAct = servicio.actualizarCliente(id, Cliente);

        return ResponseEntity.ok(ClienteAct);
    }catch(RuntimeException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}
