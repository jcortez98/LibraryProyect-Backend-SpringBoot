package com.carloscortez.webapp.biblioteca.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carloscortez.webapp.biblioteca.model.Prestamo;
import com.carloscortez.webapp.biblioteca.service.LibroService;
import com.carloscortez.webapp.biblioteca.service.PrestamoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RestController
@RequestMapping("")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;
    @Autowired
    LibroService libroService;

    @GetMapping("/prestamos")
    public ResponseEntity<?> listarPrestamos() {
        try {
            return ResponseEntity.ok(prestamoService.listarPrestamos());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontraron Prestamos");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/prestamo")
    public ResponseEntity<?> buscarPrestamoPorId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(prestamoService.buscarPrestamoPorId(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontró el Prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/cancelar-prestamo")
    public ResponseEntity<Map<String, String>> cancelarPrestamo(@RequestParam Long id) {
        Map<String,String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            if(prestamo.getVigencia()){
                libroService.cambiarDisponibilidadLibros(prestamo.getLibros());
                prestamo.setVigencia(false);
                prestamoService.guardarPrestamo(prestamo);
                response.put("message", "Prestamo cancelado con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Error");
                response.put("err", "El prestamo seleccionado no se encuentra vigente");
                return ResponseEntity.badRequest().body(response);     
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un erro al cancelar el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }
    

    @PostMapping("/prestamo")
    public ResponseEntity<Map<String,String>> agregarPrestamo(@RequestBody Prestamo prestamo) {
        Map<String, String> response = new HashMap<>();
        try {
            if(!prestamoService.verificarPrestamoVigente(prestamo)){
                if(prestamoService.limiteDeLibrosValido(prestamo)){
                    if(libroService.verificarDisponibilidadLibros(prestamo.getLibros())){
                        libroService.cambiarDisponibilidadLibros(prestamo.getLibros());
                        prestamoService.guardarPrestamo(prestamo);
                        response.put("message", "Prestamo creado con éxito");
                        return ResponseEntity.ok(response);
                    }else{
                        response.put("message", "Error");
                        response.put("err", "Alguno de los libros solicitados no esta disponible");
                        return ResponseEntity.badRequest().body(response);
                    }
                }else{
                    response.put("message", "Error");
                    response.put("err", "Ha Superado el máximo de libros");
                    return ResponseEntity.badRequest().body(response);
                }
            }else{
                response.put("message", "Error");
                response.put("err", "El cliente posee un prestamo vigente");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error");
            response.put("err", "Hubo un erro al crear el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/prestamo")
    public ResponseEntity<Map<String,String>> editarPrestamo(@RequestParam Long id, @RequestBody Prestamo prestamoNuevo) {
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            libroService.cambiarDisponibilidadLibros(prestamo.getLibros());
            if(prestamoNuevo.getVigencia()){
                libroService.cambiarDisponibilidadLibros(prestamoNuevo.getLibros());
            }
            prestamo.setCliente(prestamoNuevo.getCliente());
            prestamo.setEmpleado(prestamoNuevo.getEmpleado());
            prestamo.setFechaDeDevolucion(prestamoNuevo.getFechaDeDevolucion());
            prestamo.setFechaDePrestamo(prestamoNuevo.getFechaDePrestamo());
            prestamo.setLibros(prestamoNuevo.getLibros());
            prestamo.setVigencia(prestamoNuevo.getVigencia());
            prestamoService.guardarPrestamo(prestamo);
            response.put("message", "Prestamo modificado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al modificar el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/prestamo")
    public ResponseEntity<Map<String,String>> eliminarPrestamo(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamoService.eliminarPrestamo(prestamo);
            response.put("message", "Prestamo eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception P) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al eliminar el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
