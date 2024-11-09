package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Short> {
}
