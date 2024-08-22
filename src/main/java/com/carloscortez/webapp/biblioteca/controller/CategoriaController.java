package com.carloscortez.webapp.biblioteca.controller;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carloscortez.webapp.biblioteca.model.Categoria;
import com.carloscortez.webapp.biblioteca.service.CategoriaService;

@Controller
@RestController
@RequestMapping(value = "")
public class CategoriaController{

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<?> listarCategorias(){
        try {
            return ResponseEntity.ok(categoriaService.listarCategorias());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontraron Categorias");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/categoria")
    public ResponseEntity<?> buscarCategoriaPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontró la Categoria");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Map<String, String>> agregarCategoria(@RequestBody Categoria categoria){
        Map<String, String> response = new HashMap<>();
        try {
            if(categoriaService.guardarCategoria(categoria)){
                response.put("message", "La categoria se creó con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("err", "Categoria duplicada");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("err", "Hubo un error al crear la categoria");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/categoria")
    public ResponseEntity<Map<String, String>> editarCategoria(@RequestParam Long id, @RequestBody Categoria categoriaNueva){
        Map<String, String> response = new HashMap<>();
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            categoria.setNombreCategoria(categoriaNueva.getNombreCategoria());
            if(categoriaService.guardarCategoria(categoria)){
                response.put("message", "La categoria se ha editado con éxito");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "La categoria no se pudo editar");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            response.put("message", "La categoria no se pudo editar");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/categoria")
    public ResponseEntity<Map<String, String>> eliminarCategoria(@RequestParam Long id){
        Map<String,String> response = new HashMap<>();
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            categoriaService.eliminarCategoria(categoria);
            response.put("message", "Categoria eliminada con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "La categoria no se pudo eliminar");
            return ResponseEntity.badRequest().body(response);
        }
    }


}
