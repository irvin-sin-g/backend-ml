package com.ifmd.mercaditolibre.controller;

import com.ifmd.mercaditolibre.model.ProveedorEntity;
import com.ifmd.mercaditolibre.services.ProveedorService;

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
@RequestMapping("/api/v1/proveedores") //mapeo general Proveedores
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequiredArgsConstructor

public class ProveedorController {
    private final ProveedorService servicio;

    //endpoint ver todos los Proveedors

    @GetMapping("/")
    public ResponseEntity<List<ProveedorEntity>>listar(){
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    //consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorEntity> obtenerDetalles(@PathVariable Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id)); //200
    }

    //eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProveedorEntity> eliminar(@PathVariable Long id){
        servicio.eliminarProveedor(id);
        return ResponseEntity.noContent().build(); //204 no content
    }

    //agregar
    @PostMapping
    public ResponseEntity<ProveedorEntity> crear(@RequestBody ProveedorEntity Proveedor){
        ProveedorEntity nuevo = servicio.guardarProveedor(Proveedor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @RequestBody ProveedorEntity Proveedor){
        try{
        ProveedorEntity ProveedorAct = servicio.actualizarProveedor(id, Proveedor);

        return ResponseEntity.ok(ProveedorAct);
    }catch(RuntimeException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}