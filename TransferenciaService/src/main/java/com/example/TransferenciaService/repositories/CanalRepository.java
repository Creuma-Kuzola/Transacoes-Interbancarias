/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.TransferenciaService.repositories;

import com.example.TransferenciaService.entities.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creuma
 */
@Repository
public interface CanalRepository extends JpaRepository<Canal, Integer> {
    
}
