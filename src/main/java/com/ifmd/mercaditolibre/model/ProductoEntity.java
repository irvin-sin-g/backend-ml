package com.ifmd.mercaditolibre.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "productos")

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column
    private String imagenUrl;

    //----- Relaciones de llaves FK -----
    @ManyToOne (fetch = FetchType.EAGER) //carga de datos temprano 
                                         // para renderizado rapido, optimo para listas desplegables
    @JoinColumn(name = "categoria_id") //llave foranea de categoria
    private CategoriaEntity categoria;

    //poner aqui la de proveedor
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private ProveedorEntity proveedor;

    @JsonProperty("categoriaId")
public void setCategoriaId(Long categoriaId) {
    if (categoriaId != null) {
        CategoriaEntity cat = new CategoriaEntity();
        cat.setId(categoriaId);
        this.categoria = cat;
    }
}

@JsonProperty("proveedorId")
public void setProveedorId(Long proveedorId) {
    if (proveedorId != null) {
        ProveedorEntity prov = new ProveedorEntity();
        prov.setId(proveedorId);
        this.proveedor = prov;
    }
}
}
