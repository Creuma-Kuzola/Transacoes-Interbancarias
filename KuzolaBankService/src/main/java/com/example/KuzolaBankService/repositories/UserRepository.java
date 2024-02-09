package com.example.KuzolaBankService.repositories;

import com.example.KuzolaBankService.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByLogin(String login);
  
  
 
//  @Query("SELECT u.fkCliente.pkCliente FROM Users WHERE u.login =:username")
//  Integer findUserByUsername(String username);
}
