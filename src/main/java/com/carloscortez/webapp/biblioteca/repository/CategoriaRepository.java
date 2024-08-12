package com.carloscortez.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscortez.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
