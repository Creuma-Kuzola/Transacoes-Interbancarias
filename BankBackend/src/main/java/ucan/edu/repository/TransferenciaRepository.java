/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package ucan.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucan.edu.entities.*;
import org.springframework.stereotype.Repository;
import ucan.edu.utils.enums.StatusContaBancaria;

import java.util.List;


/**
 *
 * @author creuma
 */
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {

    @Query("SELECT t FROM Transferencia t ORDER BY t.pkTransferencia DESC ")
    public List<Transferencia> findAllDesc();

    @Query("SELECT t FROM Transferencia t WHERE t.ibanOrigem = :ibanOrigem" )
    public List<Transferencia> findAllTransacoesDebitadas(String ibanOrigem);

    @Query("SELECT t FROM Transferencia t where t.ibanDestinatario = :ibanDestino")
    public List<Transferencia> findAllTransacoesCreditadas(String ibanDestino);

}
