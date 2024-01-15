/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.TokenValidacao;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.implementacao.TokenValidacaoServiceImpl;
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
@RequestMapping("/tokenValidacao")
public class TokenValidacaoController extends BaseController{
   
    @Autowired
    TokenValidacaoServiceImpl tokenValidacaoServiceImpl;
    
    @GetMapping
    public ResponseEntity<ResponseBody> findAllTokenValidacaos()
    {
        
        List<TokenValidacao> lista = tokenValidacaoServiceImpl.findAll();
        return this.ok("Os TokenValidacao encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findTokenValidacaoByID(@PathVariable Integer id)
    {
        Optional<TokenValidacao> consulta = this.tokenValidacaoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("TokenValidacao encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("TokenValidacao não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTokenValidacao(@RequestBody TokenValidacao tokenValidacao)
    {
        return this.created("TokenValidacao adicionado com sucesso.", this.tokenValidacaoServiceImpl.criar(tokenValidacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTokenValidacao(@PathVariable("id") Integer id)
    {
        return this.ok("TokenValidacao eliminado com sucesso.", this.tokenValidacaoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTokenValidacao(@PathVariable("id") Integer id, @RequestBody TokenValidacao tokenValidacao)
    {
        return this.ok("TokenValidacao editado com sucesso.", (TokenValidacao) tokenValidacaoServiceImpl.editar(id, tokenValidacao));
    }

}
