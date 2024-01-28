/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package ucan.edu.repository;

import ucan.edu.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creuma
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>
{

    public Empresa findEmpresaByNif(@Param("nif") String nif);
    
    
    @Query("SELECT e FROM Empresa e WHERE e.nif = :nif")
    public Empresa findEmresaPorNif(@Param("nif") String  nif);
}
