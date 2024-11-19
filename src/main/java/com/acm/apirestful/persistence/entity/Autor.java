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
public class Autor implements Serializable {

    @Id
    @Column(name = "idAutor", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short id;
    private String nombre;
    private String biografia;

    /*
     * Relacion bidireccional uno a muchos [autor(1) <--> libro(n)], un autor tiene varios libros,
     * y un libro pertenece a un autor
     * Se especifica el nombre con el que es asociada la entidad actual en la otra entidad de la relación
     */
    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private Set<Libro> libros = new HashSet<>();

    public Autor(String nombre, String biografia) {
        super();
        this.nombre = nombre;
        this.biografia = biografia;
    }

}
