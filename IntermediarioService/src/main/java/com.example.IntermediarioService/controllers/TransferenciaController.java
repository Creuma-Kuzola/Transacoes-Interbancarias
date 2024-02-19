/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.IntermediarioService.controllers;

import com.example.IntermediarioService.component.BancoComponent;
import com.example.IntermediarioService.component.TransferenciaResponseComponent;
import com.example.IntermediarioService.entities.Transferencia;
import com.example.IntermediarioService.https.utils.ResponseBody;
import com.example.IntermediarioService.kafka.KafkaTransferenciaProducer;
import com.example.IntermediarioService.services.implementacao.TransferenciaServiceImpl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import com.example.IntermerdiarioService.utils.pojos.TransferenciaPOJO;
import com.example.IntermerdiarioService.utils.pojos.TransferenciaResponse;
import com.example.IntermerdiarioService.utils.pojos.jsonUtils.CustomJsonPojos;
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
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController {
    @Autowired
    TransferenciaServiceImpl transferenciaServiceImpl;

    @Autowired
    BancoComponent bancoComponent;

    @Autowired
    KafkaTransferenciaProducer kafkaTransferenciaProducer;

    @Autowired
    TransferenciaResponseComponent transferenciaResponseComponent;
    @GetMapping
    public ResponseEntity<ResponseBody> findAllTransferencias()
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

    public String criarStrToJson(TransferenciaPOJO transferenciaPOJO)
    {
        String str = "{\n"
                + "  \"pkTransferencia\": " + transferenciaPOJO.getPkTransferencia() + ",\n"
                + "   \"descricao\": \"" +  transferenciaPOJO.getDescricao() + "\",\n"
                + "    \"montante\": " + transferenciaPOJO.getMontante() + ",\n"
                + "    \"ibanDestinatario\": \"" + transferenciaPOJO.getIbanDestinatario() + "\",\n"
                + "    \"datahora\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferenciaPOJO.getDatahora()) + "\",\n"
                + "    \"fkContaBancariaOrigem\": " + transferenciaPOJO.getFkContaBancariaOrigem() + ",\n"
                + "    \"tipoTransferencia\": \"" + transferenciaPOJO.getTipoTransferencia() + "\",\n"
                + "    \"estadoTransferencia\": \"" + transferenciaPOJO.getEstadoTransferencia() + "\",\n"
                + "    \"codigoTransferencia\": \"" + transferenciaPOJO.getCodigoTransferencia() + "\",\n"
                + "    \"bancoUdentifier\":"+transferenciaPOJO.getBancoUdentifier()+"\n"
                + "}";

        return str;
    }


    // /transferencia/publishTransferencia
    @PostMapping("/publishTransferencia")
    public ResponseEntity<String> publishTranasferencia(@RequestBody TransferenciaPOJO transferencia)
    {
        String data = CustomJsonPojos.criarStrToJson(transferencia);
        kafkaTransferenciaProducer.sendMessage(data);
        return ResponseEntity.ok("Transferencia envida com sucesso no topic");
    }

    @PostMapping
    public ResponseEntity<ResponseBody> createTransferencia(@RequestBody Transferencia transferencia)
    {
        return this.created("TransferenciaDto adicionada com sucesso.", this.transferenciaServiceImpl.criar(transferencia));
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

    @PostMapping("/response2")
    public ResponseEntity<String> sendResponseTransferencia(@RequestBody TransferenciaResponse response)
    {
        String data =  CustomJsonPojos.TransferenciaResponse(response);
        this.kafkaTransferenciaProducer.sendMessageTransferenciaResponse(data);
        System.out.println(" Resposta envida com sucesso wakanda bank! " +bancoComponent.geBancoComponent().get("UUID"));
        return  ResponseEntity.ok("Resposta envida com sucesso! para o wakanda bank" +data) ;
    }

    @PostMapping("/responseTokuzola")
    public ResponseEntity<String> sendResponseTransferencia2(@RequestBody TransferenciaResponse response)
    {
        String data =  CustomJsonPojos.TransferenciaResponse(response);
        this.kafkaTransferenciaProducer.sendMessageTransferenciaResponse2(data);
        System.out.println(" Resposta envida com sucesso wakanda bank! " +bancoComponent.geBancoComponent().get("UUID"));
        return  ResponseEntity.ok("Resposta envida com sucesso! para o wakanda bank" +data) ;
    }
}
