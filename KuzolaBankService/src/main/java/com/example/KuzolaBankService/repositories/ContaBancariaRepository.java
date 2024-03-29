/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.KuzolaBankService.entities.ContaBancaria;

import java.math.BigInteger;
import java.util.HashSet;

/**
 *
 * @author creuma
 */
@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer> {

    @Query("SELECT c.numeroDeConta FROM ContaBancaria c ")
    public HashSet<BigInteger> findAllNumeroConta();
    public ContaBancaria findByIban(String iban);
    @Query("SELECT c FROM ContaBancaria c WHERE c.fkCliente.pkCliente =:cliente")
    public ContaBancaria findByCliente(Integer cliente);

    @Query("SELECT c FROM ContaBancaria c WHERE c.numeroDeConta =:numero AND c.status =:status")
    public  ContaBancaria findContaBancariaByStatus(@Param("numero") BigInteger numero, @Param("status") String status);

    @Query("SELECT c FROM ContaBancaria c WHERE c.iban =:iban AND c.status =:status")
    public  ContaBancaria findContaBancariaByStatus(@Param("iban") String iban, @Param("status") String status);

    public ContaBancaria findByNumeroDeConta(BigInteger numeroDeConta);

}
