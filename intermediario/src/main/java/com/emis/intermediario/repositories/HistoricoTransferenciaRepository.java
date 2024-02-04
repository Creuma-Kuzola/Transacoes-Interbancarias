/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.emis.intermediario.repositories;

import com.emis.intermediario.entities.HistoricoTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jussyleitecode
 */
@Repository
public interface HistoricoTransferenciaRepository extends JpaRepository<HistoricoTransferencia, Integer>
{

    @Query("SELECT t FROM HistoricoTransferencia t WHERE t.fkBanco.pkBanco = :fkBanco")
    public HistoricoTransferencia findHistoricoTransferenciaPorBanco(@Param("fkBanco") Integer fkBanco);
}
