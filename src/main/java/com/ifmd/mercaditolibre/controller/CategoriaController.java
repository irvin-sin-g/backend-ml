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

import com.ifmd.mercaditolibre.model.CategoriaEntity;
import com.ifmd.mercaditolibre.services.CategoriaService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/categorias") //mapeo general Categorias
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequiredArgsConstructor

public class CategoriaController {
    private final CategoriaService servicio;

    //endpoint ver todos los Categorias

    @GetMapping("/")
    public ResponseEntity<List<CategoriaEntity>>listar(){
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    //consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> obtenerDetalles(@PathVariable Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id)); //200
    }

    //eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaEntity> eliminar(@PathVariable Long id){
        servicio.eliminarCategoria(id);
        return ResponseEntity.noContent().build(); //204 no content
    }

    //agregar
    @PostMapping
    public ResponseEntity<CategoriaEntity> crear(@RequestBody CategoriaEntity Categoria){
        CategoriaEntity nuevo = servicio.guardarCategoria(Categoria);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @RequestBody CategoriaEntity Categoria){
        try{
        CategoriaEntity CategoriaAct = servicio.actualizarCategoria(id, Categoria);

        return ResponseEntity.ok(CategoriaAct);
    }catch(RuntimeException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}