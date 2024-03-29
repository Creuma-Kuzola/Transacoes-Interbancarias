/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.IntermediarioService.repositories;

import com.example.IntermediarioService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creuma
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByLogin(String login);
}
