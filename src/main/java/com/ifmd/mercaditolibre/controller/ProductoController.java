package com.ifmd.mercaditolibre.controller;
import com.ifmd.mercaditolibre.model.ProductoEntity;
import com.ifmd.mercaditolibre.services.ProductoService;

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
@RequestMapping("/api/v1/productos") //mapeo general productos
@CrossOrigin(origins = "http://localhost:5173")//permiso a react
@RequiredArgsConstructor

public class ProductoController {
    private final ProductoService servicio;

    //endpoint ver todos los productos

    @GetMapping("/")
    public ResponseEntity<List<ProductoEntity>>listar(){
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    //consultar por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerDetalles(@PathVariable Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id)); //200
    }

    //eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoEntity> eliminar(@PathVariable Long id){
        servicio.eliminarProducto(id);
        return ResponseEntity.noContent().build(); //204 no content
    }

    //agregar
    @PostMapping
    public ResponseEntity<ProductoEntity> crear(@RequestBody ProductoEntity producto){
        ProductoEntity nuevo = servicio.guardarProducto(producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);//201 created
    }

    //actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Long id, @RequestBody ProductoEntity producto){
        try{
        ProductoEntity productoAct = servicio.actualizarProducto(id, producto);

        return ResponseEntity.ok(productoAct);
    }catch(RuntimeException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}
