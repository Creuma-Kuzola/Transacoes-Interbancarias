/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TransferenciaService.controllers;

import com.example.TransferenciaService.entities.TipoTransferencia;
import com.example.TransferenciaService.https.utils.ResponseBody;
import com.example.TransferenciaService.services.implementacao.TipoTransferenciaServiceImpl;
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
@RequestMapping("/tipoTransferencia")
public class TipoTransferenciaController extends BaseController {
    
    @Autowired
    TipoTransferenciaServiceImpl tipoTransferenciaServiceImpl;
    
    @GetMapping
    public ResponseEntity<ResponseBody> findAllTipoTransferencias()
    {
        
        List<TipoTransferencia> lista = tipoTransferenciaServiceImpl.findAll();
        return this.ok("Tipos Transferencias encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findTipoTransferenciaByID(@PathVariable Integer id)
    {
        Optional<TipoTransferencia> consulta = this.tipoTransferenciaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("TipoTransferencia encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("TipoTransferencia não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTipoTransferencia(@RequestBody TipoTransferencia tipoTransferencia)
    {
        return this.created("TipoTransferencia adicionada com sucesso.", this.tipoTransferenciaServiceImpl.criar(tipoTransferencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTipoTransferencia(@PathVariable("id") Integer id)
    {
        return this.ok("TipoTransferencia eliminada com sucesso.", this.tipoTransferenciaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTipoTransferencia(@PathVariable("id") Integer id, @RequestBody TipoTransferencia tipoTransferencia)
    {
        return this.ok("TipoTransferencia editada com sucesso.", (TipoTransferencia) tipoTransferenciaServiceImpl.editar(id, tipoTransferencia));
    }
    
}
