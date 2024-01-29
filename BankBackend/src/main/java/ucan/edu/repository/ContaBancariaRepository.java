/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package ucan.edu.repository;

import java.util.Optional;
import ucan.edu.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ucan.edu.utils.enums.StatusContaBancaria;

/**
 *
 * @author jussyleitecode
 */
@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer>
{

    public Optional<ContaBancaria> findContaBancariaByNumeroDeConta(@Param("numeroConta") Integer numeroConta);

    @Query("SELECT c FROM ContaBancaria c WHERE c.numeroDeConta =:numeroDeConta AND c.status =:status")
    public ContaBancaria isContaBancariaActived(@Param("numeroDeConta") Integer numeroConta, @Param("status") StatusContaBancaria status);
}
