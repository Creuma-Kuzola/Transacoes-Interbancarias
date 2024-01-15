/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.KuzolaBankService.entities.Transferencia;
import org.springframework.stereotype.Repository;


/**
 *
 * @author creuma
 */
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Integer> {
    
}
