package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Libro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Short> {

    List<Libro> findLibrosByCategoriaNombreCategoriaLike(String nombreCategoria);

    List<Libro> findLibrosByAutorNombreLike(String nombre);

    @Query("""
        select p.libros
        FROM Prestamo p
        JOIN p.cliente c on c.id =:idCliente
        WHERE p.id =:idPrestamo
    """)
    List<Libro> findByPrestamoAndCliente(Short idCliente, Short idPrestamo);

    List<Libro> findLibrosByTituloLike(String titulo);

    @Modifying
    @Query(value = "update Libro l set l=:#{#libro} where l.id=:#{#titulo}")
    void updateLibroByTitulo(@Param("libro") Libro libro, @Param("titulo") String titulo);

    Boolean existsLibroByTituloAndFechaPublicacionAndAutorNombreAndCategoriaNombreCategoria(String titulo,
                                                                                             String fechaPublicacion,
                                                                                             String nombre,
                                                                                             String nombreCategoria);

    @Modifying
    @Transactional
    void deleteLibroByTitulo(String titulo);
}
