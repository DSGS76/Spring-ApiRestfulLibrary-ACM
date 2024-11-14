package com.acm.apirestful.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Categoria implements Serializable {

    @Id
    @Column(name = "idCategoria", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Short id;
    private String nombreCategoria;
    private String descripcion;

    /*
     * Relacion bidireccional uno a muchos [categoria(1) <--> libro(n)], una categoria tiene varios libros,
     * y un libro pertenece a una categoria
     * Se especifica el nombre con el que es asociada la entidad actual en la otra entidad de la relación
     */
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private Set<Libro> libros = new HashSet<>();

    public Categoria(String nombreCategoria, String descripcion) {
        super();
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

}
