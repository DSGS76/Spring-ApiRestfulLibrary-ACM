package com.acm.apirestful.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id","titulo","fechaPublicacion","disponibilidad", "descripcion", "categoria", "autor"})
public class Libro {

    @Id
    @Column(name = "idLibro", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short id;
    private String titulo;
    private String fechaPublicacion;
    private Boolean disponibilidad;
    private String descripcion;

    /*
     * Relacion bidireccional uno a muchos [categoria(1) <--> libro(n)], una categoria se presenta en varios libros,
     * y uno o varios libros pertenecen unicamente a una categoria
     * La entidad "dueña" de la relacion es la que contendrá la clave foránea, en este caso libro
     */
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "id_categoriaFK")
    @Setter
    private Categoria categoria;

    /*
     * Relacion bidireccional uno a muchos [autor(1) <--> libro(n)], un autor se presenta en varios libros,
     * y uno o varios libros pertenecen unicamente a un autor
     * La entidad "dueña" de la relacion es la que contendrá la clave foránea, en este caso libro
     */
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "id_autorFK")
    @Setter
    private Autor autor;

    /*
     * Relacion bidireccional muchos a muchos, [libro(n) <--> prestamo(n)], un libro puede tener varios
     * prestamos y un prestamo puede estar presente en varios libros
     * Se utiliza la anotacion @JoinTable, que se encarga de crear la tabla de rompimiento en la base
     * de datos, sin la necesidad de crear una entidad que la mapee directamente en nuestro programa
     * (Tabla PrestamoLibro)
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(
            name = "PrestamoLibro",
            joinColumns = {@JoinColumn(name = "idLibro")},//nombre de la clave foranea asociada al id de esta entidad,
            // que se creará en la tabla de rompimiento
            inverseJoinColumns = {@JoinColumn(name = "idPrestamo")}//nombre de la clave foranea asociada al id de la otra entidad de la relacion,
            // que se creará en la tabla de rompimiento
    )
    private Set<Prestamo> prestamos = new HashSet<>();

    public Libro(String titulo, String fechaPublicacion, Boolean disponibilidad, String descripcion) {
        super();
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.disponibilidad = disponibilidad;
        this.descripcion = descripcion;
    }
    public void addPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.getLibros().add(this);
    }


}
