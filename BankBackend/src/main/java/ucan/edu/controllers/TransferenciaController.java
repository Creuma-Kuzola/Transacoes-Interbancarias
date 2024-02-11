/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.controllers;

import java.text.SimpleDateFormat;

import ucan.edu.config.component.UserInfo;
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
import ucan.edu.kafka.KafkaTransferenciaProducer;
import ucan.edu.utils.jsonUtils.CustomJsonPojos;
import ucan.edu.utils.pojos.TransferenciaPOJO;

/**
 *
 * @author creuma
 */
@RestController
@RequestMapping("/transferencia")
public class TransferenciaController extends BaseController
{

    private final TransferenciaServiceImpl transferenciaServiceImpl;
      @Autowired
      private UserInfo userInfo;

    private final KafkaTransferenciaProducer KafkaTransferenciaProducer;

    public TransferenciaController(TransferenciaServiceImpl transferenciaServiceImpl, KafkaTransferenciaProducer KafkaTransferenciaProducer)
    {
        this.transferenciaServiceImpl = transferenciaServiceImpl;
        this.KafkaTransferenciaProducer = KafkaTransferenciaProducer;
    }



    @PostMapping("/publishTransferencia")
    public ResponseEntity<String> publishTranasferencia(@RequestBody TransferenciaPOJO transferencia)
    {
        transferencia.setFkContaBancariaOrigem(Integer.valueOf(userInfo.getUserInfo().get("accountNumber")));
        String data = CustomJsonPojos.criarStrToJson(transferencia);
        KafkaTransferenciaProducer.sendMessage(data);
        return ResponseEntity.ok("Transferencia envida com suceesso no topic");
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
        return this.created("Transferencia adicionada com sucesso.", this.transferenciaServiceImpl.criar(transferencia));
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
