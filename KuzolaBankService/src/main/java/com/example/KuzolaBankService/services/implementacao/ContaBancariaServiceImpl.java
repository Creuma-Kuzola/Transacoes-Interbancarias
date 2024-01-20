/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.entities.Cliente;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.repositories.ContaBancariaRepository;
import com.example.KuzolaBankService.services.ContaBancariaService;
import com.example.KuzolaBankService.utils.ContaBancariaDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author creuma
 */
@Service
public class ContaBancariaServiceImpl extends AbstractService<ContaBancaria, Integer> 
implements ContaBancariaService{

    @Autowired
    ContaBancariaRepository contaBancariaRepository;
    private HashSet<BigInteger> listaNumerosDeConta = new HashSet<>();

    ContaBancariaDetails contaBancariaDetails = new ContaBancariaDetails();

    public void getAllNumerosDeConta(HashSet<BigInteger> listaNumerosDeConta){

        listaNumerosDeConta = contaBancariaRepository.findAllNumeroConta();

    }
    @Override
    public ContaBancaria creatingContaBancariaByFkCliente(Cliente cliente) {

        BigInteger numeroConta = BigInteger.ZERO;
        Date data = new Date();
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setFkCliente(cliente);

        numeroConta = contaBancariaDetails.createNumeroDeConta(listaNumerosDeConta);

        contaBancaria.setNumeroDeConta(numeroConta);
        contaBancaria.setIban(contaBancariaDetails.createIban(numeroConta));
        contaBancaria.setStatus("Activo");
        contaBancaria.setDataCriacao(data);

        return  contaBancaria;

    }
}
