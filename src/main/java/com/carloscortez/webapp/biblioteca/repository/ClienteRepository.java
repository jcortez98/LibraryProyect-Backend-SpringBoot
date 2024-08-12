package com.carloscortez.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscortez.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
