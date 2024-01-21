/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.controllers;

import ucan.edu.entities.*;
import ucan.edu.services.*;
import ucan.edu.services.implementacao.*;
import ucan.edu.https.utils.ResponseBody;
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
@RequestMapping("/ContaBancaria")
public class ContaBancariaController extends BaseController {
    
    @Autowired
    ContaBancariaServiceImpl contaBancariServiceImpl;
    
    @GetMapping
    public ResponseEntity<ResponseBody> findAllContaBancaria()
    {
        
        List<ContaBancaria> lista = contaBancariServiceImpl.findAll();
        return this.ok("Contas Bancarias encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findContaBancariaByID(@PathVariable Integer id)
    {
        Optional<ContaBancaria> consulta = this.contaBancariServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Conta Bancaria encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Conta Bancaria não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createContaBancaria(@RequestBody ContaBancaria contaBancaria)
    {
        return this.created("Conta Bancaria adicionada com sucesso.", this.contaBancariServiceImpl.criar(contaBancaria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteContaBancaria(@PathVariable("id") Integer id)
    {
        return this.ok("Conta Bancaria eliminada com sucesso.", this.contaBancariServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateContaBancaria(@PathVariable("id") Integer id, @RequestBody ContaBancaria contaBancaria)
    {
        return this.ok("Conta Bancaria editada com sucesso.", (ContaBancaria) contaBancariServiceImpl.editar(id, contaBancaria));
    }
}
