package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
}