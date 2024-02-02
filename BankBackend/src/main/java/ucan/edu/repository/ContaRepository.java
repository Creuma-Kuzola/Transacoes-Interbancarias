/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package ucan.edu.repository;

import ucan.edu.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jussyleitecode
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    
    public UserDetails findContaByLogin(@Param("login")  String username);
    //public Conta findContaByLogin(@Param("username")  String username);
}
