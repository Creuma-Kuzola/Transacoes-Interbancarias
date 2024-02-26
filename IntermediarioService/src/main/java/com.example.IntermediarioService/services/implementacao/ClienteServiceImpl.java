/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.repositories.ClienteRepository;
import com.example.IntermediarioService.services.ClienteService;
import com.example.IntermediarioService.utils.pojos.ClientePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.IntermediarioService.services.implementacao.AbstractService;

/**
 *
 * @author creuma
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer>
implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    public Cliente createCliente(ClientePOJO clientePOJO)
    {
        Cliente cliente = new Cliente();
        cliente.setNome(clientePOJO.getNome());
        cliente.setIban(clientePOJO.getIban());
        cliente.setNumeroDeConta(clientePOJO.getNumeroConta());
        cliente.setFkBanco(new Banco(Integer.parseInt(clientePOJO.getFkBanco())));

        return cliente;
    }
    public Cliente saveCliente(ClientePOJO clientePOJO)
    {
        Cliente cliente = this.createCliente(clientePOJO);
        cliente = this.clienteRepository.save(cliente);
        return cliente;
    }
    
}
