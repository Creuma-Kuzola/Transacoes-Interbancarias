/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}
