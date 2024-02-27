/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.config.component.ClienteComponent;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.Cliente;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author creuma
 */
@Service
public class ClienteServiceImpl extends AbstractService<Cliente, Integer> implements ClienteService
{
    @Autowired
    ClienteComponent clienteComponent;

    public void saveClientPOJO(ContaBancaria contaBancaria, Cliente cliente)
    {
        Map<String, String> clienteMap = new HashMap<>();

        System.out.println("pkCliente: " +cliente.getPkCliente());

        clienteMap.put("pkCliente",""+cliente.getPkCliente());
        clienteMap.put("nome",cliente.getFkPessoa().getNome());
        clienteMap.put("iban", contaBancaria.getIban());
        clienteMap.put("numeroConta",""+contaBancaria.getNumeroDeConta());
        clienteMap.put("fkBanco", "1");

        clienteComponent.setClienteComponent(clienteMap);
    }
}
