package com.carloscortez.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscortez.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{

}
