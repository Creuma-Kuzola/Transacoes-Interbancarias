/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.KuzolaBankService.services;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Cliente;

/**
 *
 * @author creuma
 */
public interface ContaBancariaService<T,K> {

    ContaBancaria creatingContaBancariaByFkCliente(Cliente cliente);
}
