package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
    List<Categoria> findByNombreCategoria(String nombreCategoria);
}
