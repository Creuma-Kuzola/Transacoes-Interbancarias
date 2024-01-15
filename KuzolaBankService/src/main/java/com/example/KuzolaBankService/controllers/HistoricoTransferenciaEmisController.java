/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.HistoricoTransferenciaEmis;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.services.implementacao.HistoricoTransferenciaEmisServiceImpl;
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
@RequestMapping("/historicoTransferenciaEmis")
public class HistoricoTransferenciaEmisController extends BaseController {
    
    @Autowired
    HistoricoTransferenciaEmisServiceImpl historicoTransferenciaEmisServiceImpl;
   
    @GetMapping
    public ResponseEntity<ResponseBody> findAllHistoricoTransferenciaEmiss()
    {
        
        List<HistoricoTransferenciaEmis> lista = historicoTransferenciaEmisServiceImpl.findAll();
        return this.ok("HistoricoTransferenciaEmis encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findHistoricoTransferenciaEmisByID(@PathVariable Integer id)
    {
        Optional<HistoricoTransferenciaEmis> consulta = this.historicoTransferenciaEmisServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("HistoricoTransferenciaEmis encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("HistoricoTransferenciaEmis não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createHistoricoTransferenciaEmis(@RequestBody HistoricoTransferenciaEmis historicoTransferenciaEmis)
    {
        return this.created("HistoricoTransferenciaEmis adicionado com sucesso.", this.historicoTransferenciaEmisServiceImpl.criar(historicoTransferenciaEmis));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteHistoricoTransferenciaEmis(@PathVariable("id") Integer id)
    {
        return this.ok("HistoricoTransferenciaEmis eliminado com sucesso.", this.historicoTransferenciaEmisServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateHistoricoTransferenciaEmis(@PathVariable("id") Integer id, @RequestBody HistoricoTransferenciaEmis historicoTransferenciaEmis)
    {
        return this.ok("HistoricoTransferenciaEmis editado com sucesso.", (HistoricoTransferenciaEmis) historicoTransferenciaEmisServiceImpl.editar(id, historicoTransferenciaEmis));
    }

}
