package com.carloscortez.webapp.biblioteca.service;

import java.util.List;

import com.carloscortez.webapp.biblioteca.model.Cliente;

public interface IClienteService {
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Long id);

    public Cliente guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
}
