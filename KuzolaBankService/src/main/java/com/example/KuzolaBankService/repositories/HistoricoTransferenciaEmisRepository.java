/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import com.example.KuzolaBankService.entities.HistoricoTransferenciaEmis;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author creuma
 */
public interface HistoricoTransferenciaEmisRepository extends JpaRepository<HistoricoTransferenciaEmis, Integer> {
    
}
