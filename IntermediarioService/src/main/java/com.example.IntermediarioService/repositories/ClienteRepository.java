/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.IntermediarioService.repositories;

import com.example.IntermediarioService.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creuma
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}
