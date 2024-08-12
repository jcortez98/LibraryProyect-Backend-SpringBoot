package com.carloscortez.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscortez.webapp.biblioteca.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
