package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloscortez.webapp.biblioteca.model.Libro;
import com.carloscortez.webapp.biblioteca.repository.LibroRepository;

@Service
public class LibroService implements ILibroService {

    @Autowired
    LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepository.delete(libro);
    }

    @Override
    public void cambiarDisponibilidadLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            Libro libroModificado = buscarLibroPorId(libro.getId());
            libroModificado.setDisponibilidad(!libroModificado.getDisponibilidad());
            guardarLibro(libroModificado);
        }
    }

    @Override
    public boolean verificarDisponibilidadLibros(List<Libro> libros) {
        Boolean flag = true;
        for (Libro libro : libros) {
            Libro libroPoblado = buscarLibroPorId(libro.getId());
            if(!libroPoblado.getDisponibilidad()){
                flag = false;
            }
        }
        return flag;
    }

}
