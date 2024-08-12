package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import com.carloscortez.webapp.biblioteca.model.Libro;

public interface ILibroService {
    public List<Libro> listarLibros();

    public Libro guardarLibro(Libro libro);
    
    public Libro buscarLibroPorId(Long id);

    public void eliminarLibro(Libro libro);

    public void cambiarDisponibilidadLibros(List<Libro> libros);

    public boolean verificarDisponibilidadLibros(List<Libro> libros);
}
