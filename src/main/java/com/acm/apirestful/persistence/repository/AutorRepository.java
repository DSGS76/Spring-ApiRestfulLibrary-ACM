package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Short> {
    List<Autor> findByNombre(String nombre);
}
