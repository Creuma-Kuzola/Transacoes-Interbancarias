/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.KuzolaBankService.repositories;

import com.example.KuzolaBankService.entities.TokenValidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author creuma
 */
@Repository
public interface TokenValidacaoRepository extends JpaRepository<TokenValidacao, Integer> {
    
}
