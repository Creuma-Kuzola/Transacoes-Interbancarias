/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.Cliente;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.implementacao.ClienteServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author creuma
 */
@RestController
@RequestMapping("/cliente")
public class ClienteController extends BaseController {
    
    @Autowired
    ClienteServiceImpl clienteServiceImpl;
   
    @GetMapping
    public ResponseEntity<ResponseBody> findAllClientes()
    {
        
        List<Cliente> lista = clienteServiceImpl.findAll();
        return this.ok("Clientes encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findClienteByID(@PathVariable Integer id)
    {
        Optional<Cliente> consulta = this.clienteServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Cliente encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Cliente não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createCliente(@RequestBody Cliente cliente)
    {
        return this.created("Cliente adicionado com sucesso.", this.clienteServiceImpl.criar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteCliente(@PathVariable("id") Integer id)
    {
        return this.ok("Cliente eliminado com sucesso.", this.clienteServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente)
    {
        return this.ok("Cliente editado com sucesso.", (Cliente) clienteServiceImpl.editar(id, cliente));
    }
}
