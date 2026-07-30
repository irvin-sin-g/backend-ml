package com.ifmd.mercaditolibre.services;
import com.ifmd.mercaditolibre.model.ProductoEntity;
import com.ifmd.mercaditolibre.repository.ProductoRepository;
import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;


@Service 
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;

    //leer todos los registros
    @Transactional(readOnly = true)
    public List<ProductoEntity> obtenerTodos(){
    return repository.findAll();
    }

    //obtener por id
    @Transactional(readOnly=true)
    public ProductoEntity obtenerPorId(Long id){
        return repository.findById(id).
        orElseThrow(()-> new RuntimeException("Productos no encontrado: "));
    }

    //guardar un registro
    @Transactional
    public ProductoEntity guardarProducto(ProductoEntity producto){
        return repository.save(producto);
        //aqui se guardan todos los productos
    }

    //eliminar registro
    @Transactional
    public void eliminarProducto(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("No se encontro el producto");
        }
    repository.deleteById(id);
    }

    //actualizar registro
    @Transactional
    public ProductoEntity actualizarProducto(Long id, ProductoEntity detalleProducto){
        ProductoEntity productoExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Producto no existe !"));

        BeanUtils.copyProperties(detalleProducto, productoExistente, "id");
        return repository.save(productoExistente);
    }
}
