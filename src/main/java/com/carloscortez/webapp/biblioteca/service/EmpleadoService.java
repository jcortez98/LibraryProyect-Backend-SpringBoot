package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloscortez.webapp.biblioteca.model.Empleado;
import com.carloscortez.webapp.biblioteca.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarEmpleado(Empleado empleado) {
        if(!verificarDpiDuplicado(empleado)){ //DPI NO DUPLICADO
            empleadoRepository.save(empleado);
            return true;
        }else{//DPI SI DUPLICADO
            return false;
        }
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }

    @Override
    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo) {
        List<Empleado> empleados = listarEmpleados();
        Boolean flag = false;

        for (Empleado empleado : empleados) {
            if(empleadoNuevo.getDpi().equals(empleado.getDpi()) && !empleado.getId().equals(empleadoNuevo.getId())){
                flag = true; // SI HAY DPI DUPLICADO
            }
        }

        return flag;
    }

}
