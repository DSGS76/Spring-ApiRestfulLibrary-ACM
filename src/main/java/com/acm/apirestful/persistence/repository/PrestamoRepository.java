package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Short> {
}
