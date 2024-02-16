/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import com.example.KuzolaBankService.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


/**
 *
 * @author creuma
 */
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {

    @Query("SELECT t FROM Transferencia t ORDER BY t.pkTransferencia DESC ")
    public List<Transferencia> findAllDesc();

    @Query("SELECT t FROM Transferencia t WHERE t.fkContaBancariaOrigem.pkContaBancaria = :fkContaBancariaOrigem" )
    public List<Transferencia> findAllTransacoesDebitadas(Integer fkContaBancariaOrigem);

    @Query("SELECT t FROM Transferencia t where t.ibanDestinatario = :ibanDestino")
    public List<Transferencia> findAllTransacoesCreditadas(String ibanDestino);

}
