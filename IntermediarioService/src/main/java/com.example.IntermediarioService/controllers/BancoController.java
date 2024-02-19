/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.entities.Banco;
import com.example.IntermediarioService.https.utils.ResponseBody;
import com.example.IntermediarioService.services.implementacao.BancoServiceImpl;

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
@RequestMapping("/banco")
public class BancoController  extends BaseController{
    
    @Autowired
    BancoServiceImpl bancoServiceImpl;

    @GetMapping
    public ResponseEntity<ResponseBody> findAllBancos()
    {
        List<Banco> lista = bancoServiceImpl.findAll();
        return this.ok("Bancos encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findBancoByID(@PathVariable Integer id)
    {
        Optional<Banco> consulta = this.bancoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Banco encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Banco não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createBanco(@RequestBody Banco Banco)
    {
        ResponseEntity<ResponseBody> BancoResponse = this.created("Banco adicionado com sucesso.", this.bancoServiceImpl.criar(Banco));
        return BancoResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteBanco(@PathVariable("id") Integer id)
    {
        return this.ok("Banco eliminado com sucesso.", this.bancoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateBanco(@PathVariable("id") Integer id, @RequestBody Banco banco)
    {
        return this.ok("Banco editado com sucesso.", (Banco) bancoServiceImpl.editar(id, banco));
    }
    
}
