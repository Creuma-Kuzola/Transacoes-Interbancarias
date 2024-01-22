/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.controllers;

import com.example.TransferenciaService.entities.Canal;
import com.example.TransferenciaService.https.utils.ResponseBody;
import com.example.TransferenciaService.services.implementacao.CanalServiceImpl;
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
@RequestMapping("/canal")
public class CanalController extends BaseController {
    
    @Autowired
    CanalServiceImpl canalServiceImpl;
    
    @GetMapping
    public ResponseEntity<ResponseBody> findAllCanals()
    {
        
        List<Canal> lista = canalServiceImpl.findAll();
        return this.ok("Canais encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findCanalByID(@PathVariable Integer id)
    {
        Optional<Canal> consulta = this.canalServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Canal encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Canal não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createCanal(@RequestBody Canal canal)
    {
        return this.created("Canal adicionado com sucesso.", this.canalServiceImpl.criar(canal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteCanal(@PathVariable("id") Integer id)
    {
        return this.ok("Canal eliminado com sucesso.", this.canalServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateCanal(@PathVariable("id") Integer id, @RequestBody Canal canal)
    {
        return this.ok("Canal editado com sucesso.", (Canal) canalServiceImpl.editar(id, canal));
    }
}
