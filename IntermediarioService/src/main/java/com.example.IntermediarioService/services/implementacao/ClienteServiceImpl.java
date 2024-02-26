/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.services.implementacao;

import com.example.IntermediarioService.entities.Cliente;
import com.example.IntermediarioService.repositories.ClienteRepository;
import com.example.IntermediarioService.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author creuma
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer>
implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Optional<Cliente> findById(Integer idCliente){
       return clienteRepository.findById(idCliente);
    }


}
