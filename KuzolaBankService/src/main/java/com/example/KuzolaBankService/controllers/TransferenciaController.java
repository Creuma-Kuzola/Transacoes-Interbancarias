/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.kafka.TransferenciaJsonKafkaProducer;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController{
    
    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;

    TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer;

    @Autowired
    ContaBancariaServiceImpl contaBancariaServiceImpl;

    public TransferenciaController(TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer) {
        this.transferenciaJsonKafkaProducer = transferenciaJsonKafkaProducer;
    }

    @GetMapping
    public ResponseEntity<ResponseBody> findAllTransferencia()
    {
        List<Transferencia> lista = transferenciaServiceImpl.findAll();
        return this.ok("Transferencias encontradas com sucesso!", lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody> findTransferenciaByID(@PathVariable Integer id)
    {
        Optional<Transferencia> consulta = this.transferenciaServiceImpl.findById(id);
        if (consulta.isPresent())
        {
            return this.ok("Transferencia encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("Transferencia não encontrada", null);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia)
    {
        System.out.println("Objecto transferencia"+ transferencia);

       if (contaBancariaServiceImpl.isValidIban(transferencia.getIbanDestinatario())) {

           if (contaBancariaServiceImpl.isValidTheSizeOfIban(transferencia.getIbanDestinatario())) {

               if (contaBancariaServiceImpl.existsIban(transferencia.getIbanDestinatario())) {

                   return this.created("Transferencia enviada com sucesso.", this.transferenciaServiceImpl.criar(transferencia));
               }
               return this.naoEncontrado("ERRO: Este IBAN não existe", null);
           }
           return this.naoEncontrado("ERRO: Este IBAN é inválido. O IBAN deve ter apenas 17 dígitos", null);
       }
       return this.naoEncontrado("ERRO: O IBAN é inválido", null);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTransferencia(@PathVariable("id") Integer id)
    {
        return this.ok("Transferencia eliminada com sucesso.", this.transferenciaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTransferencia(@PathVariable("id") Integer id, @RequestBody Transferencia transferencia)
    {
        return this.ok("Transferencia editada com sucesso.", (Transferencia) transferenciaServiceImpl.editar(id, transferencia));
    }

}
