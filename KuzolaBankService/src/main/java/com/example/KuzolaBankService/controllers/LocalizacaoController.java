/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.Localizacao;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.implementacao.LocalizacaoServiceImpl;
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
@RequestMapping("/localizacao")
public class LocalizacaoController extends BaseController{
    
    @Autowired
    LocalizacaoServiceImpl localizacaoServiceImpl;
 
    @GetMapping
    public ResponseEntity<ResponseBody> findAllLocalizacao()
    {
        
        List<Localizacao> lista = localizacaoServiceImpl.findAll();
        return this.ok("Localizacões encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findLocalizacaoByID(@PathVariable Integer id)
    {
        Optional<Localizacao> consulta = this.localizacaoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Localizacao encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Localizacao não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createLocalizacao(@RequestBody Localizacao localizacao)
    {
        return this.created("Localizacao adicionada com sucesso.", this.localizacaoServiceImpl.criar(localizacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteLocalizacao(@PathVariable("id") Integer id)
    {
        return this.ok("Localizacao eliminada com sucesso.", this.localizacaoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateLocalizacao(@PathVariable("id") Integer id, @RequestBody Localizacao localizacao)
    {
        return this.ok("Localizacao editada com sucesso.", (Localizacao) localizacaoServiceImpl.editar(id, localizacao));
    }

    
}
