/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.entities.User;
import com.example.IntermediarioService.https.utils.ResponseBody;
import com.example.IntermediarioService.services.implementacao.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author creuma
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    
    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllUsers()
    {
        List<User> lista = userServiceImpl.findAll();
        return this.ok("Users encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findUserByID(@PathVariable Integer id)
    {
        Optional<User> consulta = this.userServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("User encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("User não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createUser(@RequestBody User User)
    {
        ResponseEntity<ResponseBody> UserResponse = this.created("User adicionado com sucesso.", this.userServiceImpl.criar(User));
        return UserResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteUser(@PathVariable("id") Integer id)
    {
        return this.ok("User eliminado com sucesso.", this.userServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateUser(@PathVariable("id") Integer id, @RequestBody User user)
    {
        return this.ok("User editado com sucesso.", (User) userServiceImpl.editar(id, user));
    }
}
