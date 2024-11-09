package com.acm.apirestful.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@NoArgsConstructor
@ToString(of = {
        "id",
        "fechaInicioPrestamo",
        "fechaFinPrestamo"
})
public class Prestamo {

    @Id
    @Column(name = "idPrestamo",nullable = false,unique = true)
    private Short id;
    @Setter
    private LocalDate fechaInicioPrestamo;
    @Setter
    private LocalDate fechaFinPrestamo;

    /*
     * Relacion bidireccional uno a muchos [cliente (1) <--> prestamo(n)], un cliente se presenta en varios prestamos,
     * y uno o varios prestamos pertenecen unicamente a un cliente
     * La entidad "dueña" de la relacion es la que contendrá la clave foránea, en este caso venta
     */
    @ManyToOne
    @JoinColumn(name = "id_clienteFK")
    @Setter
    private Cliente cliente;

    /*
     * Relacion bidireccional muchos a muchos, [prestamo(n) <--> libro(n)], varios prestamos
     * agrupan varios libros
     * Se especifica el nombre con el que es asociada la entidad actual en la otra entidad de la relación
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "prestamos", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Libro> libros = new HashSet<>();

    public Prestamo(Short id, LocalDate fechaInicioPrestamo, LocalDate fechaFinPrestamo) {
        this.id = id;
        this.fechaInicioPrestamo = fechaInicioPrestamo;
        this.fechaFinPrestamo = fechaFinPrestamo;
    }
    public Prestamo(Short id, LocalDate fechaInicioPrestamo, LocalDate fechaFinPrestamo, Cliente cliente) {
        this.id = id;
        this.fechaInicioPrestamo = fechaInicioPrestamo;
        this.fechaFinPrestamo = fechaFinPrestamo;
        this.cliente = cliente;
    }

}
