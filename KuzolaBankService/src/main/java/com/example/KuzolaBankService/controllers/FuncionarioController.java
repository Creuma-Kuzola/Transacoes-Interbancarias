/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.Funcionario;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.implementacao.FuncionarioServiceImpl;
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
@RequestMapping("/funcionario")
public class FuncionarioController extends BaseController {
    
    @Autowired
    FuncionarioServiceImpl funcionarioServiceImpl;
    
    @GetMapping
    public ResponseEntity<ResponseBody> findAllFuncionario()
    {
        
        List<Funcionario> lista = funcionarioServiceImpl.findAll();
        return this.ok("Funcionarios encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findFuncionarioByID(@PathVariable Integer id)
    {
        Optional<Funcionario> consulta = this.funcionarioServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Funcionario encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Funcionario não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createFuncionario(@RequestBody Funcionario funcionario)
    {
        return this.created("Funcionario adicionado com sucesso.", this.funcionarioServiceImpl.criar(funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteFuncionario(@PathVariable("id") Integer id)
    {
        return this.ok("Funcionario eliminado com sucesso.", this.funcionarioServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateFuncionario(@PathVariable("id") Integer id, @RequestBody Funcionario funcionario)
    {
        return this.ok("Funcionario editado com sucesso.", (Funcionario) funcionarioServiceImpl.editar(id, funcionario));
    }

    
}
