package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import com.carloscortez.webapp.biblioteca.model.Prestamo;

public interface IPrestamosService {

    public List<Prestamo> listarPrestamos();

    public Prestamo buscarPrestamoPorId(Long id);

    public Boolean guardarPrestamo(Prestamo prestamo);

    public void eliminarPrestamo(Prestamo prestamo);

    public Boolean limiteDeLibrosValido(Prestamo prestamo);

    public Boolean verificarPrestamoVigente(Prestamo prestamo);
}
