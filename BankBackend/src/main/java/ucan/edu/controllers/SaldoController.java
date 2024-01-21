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
@RequestMapping("/saldo")
public class SaldoController extends BaseController {
    
    @Autowired
    SaldoServiceImpl saldoServiceImpl;
   
    @GetMapping
    public ResponseEntity<ResponseBody> findAllSaldos()
    {
        
        List<Saldo> lista = saldoServiceImpl.findAll();
        return this.ok("Saldos encontrados com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findSaldoByID(@PathVariable Integer id)
    {
        Optional<Saldo> consulta = this.saldoServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Saldo encontrado com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Saldo não encontrado", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createSaldo(@RequestBody Saldo saldo)
    {
        return this.created("Saldo adicionado com sucesso.", this.saldoServiceImpl.criar(saldo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteSaldo(@PathVariable("id") Integer id)
    {
        return this.ok("Saldo eliminado com sucesso.", this.saldoServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateSaldo(@PathVariable("id") Integer id, @RequestBody Saldo saldo)
    {
        return this.ok("Saldo editado com sucesso.", (Saldo) saldoServiceImpl.editar(id, saldo));
    }

    
}
