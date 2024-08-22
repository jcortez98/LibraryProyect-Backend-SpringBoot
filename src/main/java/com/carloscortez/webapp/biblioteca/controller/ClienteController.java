package com.carloscortez.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.carloscortez.webapp.biblioteca.model.Cliente;
import com.carloscortez.webapp.biblioteca.service.ClienteService;

@Controller
@RestController
@RequestMapping("")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<?> listarClientes(){
        try {
            return ResponseEntity.ok(clienteService.listarClientes());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontraron Clientes");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> buscarClientePorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(clienteService.buscarClientePorId(id));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error");
            response.put("err", "No se encontró el Cliente");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String,String>> agregarCliente(@RequestBody Cliente cliente){
        Map<String, String> response = new HashMap<>();
        try {
            clienteService.guardarCliente(cliente);
            response.put("message", "Cliente creado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al crear el cliente");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/cliente")
    public ResponseEntity<Map<String,String>> editarCliente(@RequestParam Long id, @RequestBody Cliente nuevoCliente) {
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            cliente.setNombre(nuevoCliente.getNombre());
            cliente.setApellido(nuevoCliente.getApellido());
            cliente.setTelefono(nuevoCliente.getTelefono());
            clienteService.guardarCliente(cliente);
            response.put("message", "El Cliente se modifico con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al modificar el cliente");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, String>> eliminarCliente(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            clienteService.eliminarCliente(cliente);
            response.put("message", "El Cliente se eliminó con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al eliminar el cliente");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
