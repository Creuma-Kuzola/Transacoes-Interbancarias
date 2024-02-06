/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.emis.intermediario.repositories;

import com.emis.intermediario.entities.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jussyleitecode
 */

@Repository
public interface BancoRepository extends JpaRepository<Banco, Integer>
{
    
}
