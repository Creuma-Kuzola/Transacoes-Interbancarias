/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.emis.intermediario.repositories;

import com.emis.intermediario.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jussyleitecode
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

    public User findUserByLogin(@Param("username") String username);
}
