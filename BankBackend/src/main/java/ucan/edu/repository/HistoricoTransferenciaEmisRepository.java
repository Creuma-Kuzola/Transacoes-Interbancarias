/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package ucan.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucan.edu.entities.*;


/**
 *
 * @author creuma
 */

@Repository
public interface HistoricoTransferenciaEmisRepository extends JpaRepository<HistoricoTransferenciaEmis, Integer> {
    
}
