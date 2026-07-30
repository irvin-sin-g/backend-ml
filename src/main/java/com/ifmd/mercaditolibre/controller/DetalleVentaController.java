package com.ifmd.mercaditolibre.controller;

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

import com.ifmd.mercaditolibre.model.DetalleVentaEntity;
import com.ifmd.mercaditolibre.services.DetalleVentaService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/detalleventas") //mapeo general DetalleVentas
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequiredArgsConstructor

public class DetalleVentaController {
    private final DetalleVentaService servicio;

    //endpoint ver todos los DetalleVentas

    @GetMapping("/")
    public ResponseEntity<List<DetalleVentaEntity>>listar(){
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    //consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> obtenerDetalles(@PathVariable Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id)); //200
    }

    //eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<DetalleVentaEntity> eliminar(@PathVariable Long id){
        servicio.eliminarDetalleVenta(id);
        return ResponseEntity.noContent().build(); //204 no content
    }

    //agregar
    @PostMapping("/")
    public ResponseEntity<DetalleVentaEntity> crear(@RequestBody DetalleVentaEntity DetalleVenta){
        DetalleVentaEntity nuevo = servicio.guardarDetalleVenta(DetalleVenta);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @RequestBody DetalleVentaEntity DetalleVenta){
        try{
        DetalleVentaEntity DetalleVentaAct = servicio.actualizarDetalleVenta(id, DetalleVenta);

        return ResponseEntity.ok(DetalleVentaAct);
    }catch(RuntimeException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}