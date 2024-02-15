/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.controllers;

import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.entities.Transferencia;
import com.example.KuzolaBankService.https.utils.ResponseBody;
import com.example.KuzolaBankService.kafka.KafkaTransferenciaProducer;
import com.example.KuzolaBankService.kafka.TransferenciaJsonKafkaProducer;
import com.example.KuzolaBankService.services.implementacao.ContaBancariaServiceImpl;
import com.example.KuzolaBankService.services.implementacao.TransferenciaServiceImpl;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.KuzolaBankService.utils.GsonLocalDateAdapter;
import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaResponse;
import com.google.gson.*;
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
import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.config.component.TransferenciaResponseComponent;
/**
 *
 * @author creuma
 */
@RestController
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController
{
    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;
    TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer;
    @Autowired
    ContaBancariaServiceImpl contaBancariaServiceImpl;

    @Autowired
    UserInfo userInfo;

    //777
    private String welcome;

    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;

    @Autowired
    private KafkaTransferenciaProducer kafkaTransferenciaProducer;

    @Autowired
    GsonLocalDateAdapter gsonLocalDateAdapter;
    public TransferenciaController(TransferenciaJsonKafkaProducer transferenciaJsonKafkaProducer)
    {
        this.transferenciaJsonKafkaProducer = transferenciaJsonKafkaProducer;
    }

    @GetMapping("/teste")
    public String transferencia()
    {
        return "Fazer a transferencia";
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
            return this.ok("TransferenciaDto encontrada com sucesso.", consulta.get());
        }
        return this.naoEncontrado("TransferenciaDto não encontrada", null);
    }


    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia)
    {
        System.out.println("TRAnsferencia"+ transferencia);
        TransferenciaPOJO transferenciaPOJO = transferenciaServiceImpl.convertingIntoTransferenciaPOJO(transferencia, userInfo.getUserInfo().get("iban"));

        String data = CustomJsonPojos.criarStrToJson(transferenciaPOJO);
        System.out.println("Data Json"+ data);
        if (transferenciaServiceImpl.isTransferenciaInformationValid(transferencia.getIbanDestinatario(), transferencia.getMontante(), userInfo.getUserInfo().get("iban")));
        {
            transferenciaJsonKafkaProducer.sendMessageTransferenciaIntraBancaria(transferenciaPOJO.toString());
            return this.created("Transferencia criada com sucesso",this.transferenciaServiceImpl.criar(transferencia));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody> deleteTransferencia(@PathVariable("id") Integer id)
    {
        return this.ok("TransferenciaDto eliminada com sucesso.", this.transferenciaServiceImpl.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updateTransferencia(@PathVariable("id") Integer id, @RequestBody Transferencia transferencia)
    {
        return this.ok("TransferenciaDto editada com sucesso.", (Transferencia) transferenciaServiceImpl.editar(id, transferencia));
    }


    // http://localhost:8080//transferencia/response
    @PostMapping("/response")
    public ResponseEntity<String> sendResponseTransferencia(@RequestBody TransferenciaResponse response)
    {
        response.setDescricao(transferenciaResponseComponent.getTransferenciaResponse().get("descricao"));
        response.setStatus(transferenciaResponseComponent.getTransferenciaResponse().get("status").equals("true") ? true : false) ;

      String data =  CustomJsonPojos.TransferenciaResponse(response);
       this.kafkaTransferenciaProducer.sendMessageResponse(data);
        System.out.println(" Resposta envida com sucesso! ");
      return  ResponseEntity.ok("Resposta envida com sucesso!" +data) ;
    }

}
