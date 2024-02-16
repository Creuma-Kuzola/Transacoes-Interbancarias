/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ucan.edu.component.TransferenciaMessage;
import ucan.edu.config.component.TransferenciaComponent;
import ucan.edu.config.component.UserInfo;
import ucan.edu.entities.*;
import ucan.edu.services.*;
import ucan.edu.services.implementacao.*;
import ucan.edu.https.utils.ResponseBody;

import java.util.*;

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
import ucan.edu.utils.dates.DateUtils;
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
    ContaBancariaServiceImpl contaBancariaServiceImpl;
      @Autowired
      private UserInfo userInfo;

      @Autowired
    private TransferenciaComponent transferenciaComponent;
      @Autowired
      private TransferenciaMessage transferenciaMessage;

    private final KafkaTransferenciaProducer KafkaTransferenciaProducer;

    public TransferenciaController(TransferenciaServiceImpl transferenciaServiceImpl, KafkaTransferenciaProducer KafkaTransferenciaProducer)
    {
        this.transferenciaServiceImpl = transferenciaServiceImpl;
        this.KafkaTransferenciaProducer = KafkaTransferenciaProducer;
    }

    @PostMapping("/publishTransferencia")
    public ResponseEntity<String> publishTranasferencia(@RequestBody TransferenciaPOJO transferencia)
    {
        String montaneStr = transferencia.getMontante().toString();
        BigDecimal bigDecimal = new BigDecimal(montaneStr);
        System.out.println("valor: " +montaneStr);
        Integer isSaldoEnought = contaBancariaServiceImpl
                .isSaldoPositiveToTransfer(Integer.valueOf(userInfo.getUserInfo().get("accountNumber")), transferencia.getMontante());
        if (isSaldoEnought != -1)
        {
            transferencia.setFkContaBancariaOrigem(Integer.valueOf(userInfo.getUserInfo().get("accountNumber")));
            transferencia.setDatahora(new Date());

            saveTransferComponent(transferencia);
            String data = CustomJsonPojos.criarStrToJson(transferencia);
            KafkaTransferenciaProducer.sendMessage(data);
            try {
                Thread.sleep(9000);


                return transferenciaMessage.getMessage().get("status").equals("true")  ?
                        ResponseEntity.ok("Message: "+transferenciaMessage.getMessage().get("message"))
                        :
                        ResponseEntity.ok("Message: "+transferenciaMessage.getMessage().get("message"));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return ResponseEntity.ok("Message:  Você possui saldo insuficiente, para efectuar a transfências!" +isSaldoEnought);
        }
    }



    //"datahora":"2024-02-10 16:11:20",

    private void saveTransferComponent(TransferenciaPOJO transferencia)  {
        Map<String, String> transferenciaItems = new HashMap<>();

        transferenciaItems.put("descricao", transferencia.getDescricao());
        transferenciaItems.put("montante", transferencia.getMontante().toString());
        transferenciaItems.put("ibanDestinatario", transferencia.getIbanDestinatario());
        transferenciaItems.put("datahora","" + transferencia.getDatahora());

        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("tipoTransferencia", transferencia.getTipoTransferencia());
        transferenciaItems.put("estadoTransferencia", transferencia.getEstadoTransferencia());
        transferenciaItems.put("codigoTransferencia", transferencia.getCodigoTransferencia());
        transferenciaComponent.setTransferenciaResponse(transferenciaItems);
        System.out.println("data: " + transferenciaComponent.getTransferenciaResponse().get("datahora"));
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
