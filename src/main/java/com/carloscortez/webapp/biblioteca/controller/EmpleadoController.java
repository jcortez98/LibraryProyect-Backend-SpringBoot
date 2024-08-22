package com.carloscortez.webapp.biblioteca.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carloscortez.webapp.biblioteca.model.Empleado;
import com.carloscortez.webapp.biblioteca.service.EmpleadoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RestController
@RequestMapping("")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public ResponseEntity<?> listarEmpleados(){
        try {
            return ResponseEntity.ok(empleadoService.listarEmpleados());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontraron Empleados");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/empleado")
    public ResponseEntity<?> buscarEmpleadoPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontró el Empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<Map<String,String>> agregarEmpleado(@RequestBody Empleado empleado) {
        Map<String, String> response = new HashMap<>();
        try {
            if(empleadoService.guardarEmpleado(empleado)){
                response.put("message", "Empleado creado con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Error");
                response.put("err", "El empleado no se registro, por DPI duplicado");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al crear el empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/empleado")
    public ResponseEntity<Map<String,String>> editarEmpleado(@RequestParam Long id, @RequestBody Empleado empleadoNuevo) {
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleadoNuevo.getDpi());
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            if(empleadoService.guardarEmpleado(empleado)){
                response.put("message", "Empleado modificado con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Error");
                response.put("err", "Hubo un error al modificar el empleado");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al modificar el empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("message", "Empleado eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al modificar el empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
