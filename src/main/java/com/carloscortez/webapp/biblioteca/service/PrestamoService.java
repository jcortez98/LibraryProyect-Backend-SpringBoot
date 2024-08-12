package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloscortez.webapp.biblioteca.model.Prestamo;
import com.carloscortez.webapp.biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoService implements IPrestamosService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarPrestamo(Prestamo prestamo) {
        prestamoRepository.save(prestamo);
        return true;
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamoRepository.delete(prestamo);
    }

    @Override
    public Boolean limiteDeLibrosValido(Prestamo prestamo) {
        Boolean flag = false;
        if(prestamo.getLibros().size() <= 3){
            flag = Boolean.TRUE;
        }
        return flag;
    }

    @Override
    public Boolean verificarPrestamoVigente(Prestamo prestamo) {
        Boolean flag = false;
        List<Prestamo> prestamos = listarPrestamos();
        for (Prestamo p : prestamos) {
            if(p.getCliente().getDpi().equals(prestamo.getCliente().getDpi()) && p.getVigencia()){
                flag = true;
                break;
            }
        }
        return flag;
    }

}
