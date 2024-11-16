package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Short> {

    boolean existsByIdAndClienteUserUsername(Short id, String username);

}
