package com.acm.apirestful.persistence.repository;

import com.acm.apirestful.persistence.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Short> {
}
