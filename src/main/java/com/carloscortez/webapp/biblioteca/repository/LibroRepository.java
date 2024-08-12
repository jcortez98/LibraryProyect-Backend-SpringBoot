package com.carloscortez.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscortez.webapp.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
