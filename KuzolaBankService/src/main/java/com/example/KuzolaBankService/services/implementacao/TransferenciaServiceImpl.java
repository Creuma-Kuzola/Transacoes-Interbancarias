/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.KuzolaBankService.services.implementacao;

import com.example.KuzolaBankService.config.component.TransferenciaComponent;
import com.example.KuzolaBankService.config.component.TransferenciaMessage;
import com.example.KuzolaBankService.config.component.UserInfo;
import com.example.KuzolaBankService.dto.TransferenciaDto;
import com.example.KuzolaBankService.entities.ContaBancaria;
import com.example.KuzolaBankService.enums.DetalhesBanco;
import com.example.KuzolaBankService.kafka.KafkaTransferenciaProducer;
import com.example.KuzolaBankService.repositories.ContaBancariaRepository;
import com.example.KuzolaBankService.repositories.TransferenciaRepository;
import com.example.KuzolaBankService.services.TransferenciaService;
import com.example.KuzolaBankService.utils.jsonUtils.CustomJsonPojos;
import com.example.KuzolaBankService.utils.pojos.TransferenciaCustomPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJO;
import com.example.KuzolaBankService.utils.pojos.TransferenciaPOJOEmis;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.KuzolaBankService.entities.Transferencia;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author creuma
 */
@Service
public class TransferenciaServiceImpl extends AbstractService<Transferencia, Integer>
implements TransferenciaService {

    @Autowired
    public ContaBancariaServiceImpl contaBancariaService;

    @Autowired
    ContaBancariaRepository contaBancariaRepository;

    @Autowired
    TransferenciaComponent transferenciaComponent;

    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaRepository transferenciaRepository;

    @Autowired
    KafkaTransferenciaProducer kafkaTransferenciaProducer;

    //-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.

    public Integer getCondigoTransferencia()
    {
        Random random = new Random();
        // Gerar um número aleatório com 4 dígitos
        Integer codigo = random.nextInt(9000) + 100000;
        //System.out.println("BANKNUMBER: " +BANKNUMBER);
        // Exibir o número gerado
        System.out.println("Número Aleatório: " + codigo);
        return codigo;
    }

    public  Integer isValidInformationIban(String iban){

        if(contaBancariaService.isValidTheSizeOfIban(iban)){
            if (contaBancariaService.isKuzolaBankIban(iban)){
                return 1;
            }
            return 2;
        }
        return 3;
    }

    public boolean isTransferenciaInformationValid(String ibanDestino, BigDecimal montante, String ibanOrigem) {

        if (contaBancariaService.existsIban(ibanDestino)) {
            if (ibanDestino != ibanOrigem) {
                ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(ibanOrigem);

                if (contaBancariaService.isValidMontante(contaBancaria, montante) != -1) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;

    }

    public TransferenciaPOJO convertingIntoTransferenciaPOJO(Transferencia transferencia, String IbanOrigem)
    {
        TransferenciaPOJO transferenciaPOJO = new TransferenciaPOJO();
        transferenciaPOJO.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaPOJO.setDatahora(transferencia.getDatahora());
        transferenciaPOJO.setDescricao(transferencia.getDescricao());
        transferenciaPOJO.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaPOJO.setMontante(transferencia.getMontante());
        transferenciaPOJO.setEstadoTransferencia(transferencia.getEstadoTransferencia());
        transferenciaPOJO.setibanOrigem(transferencia.getIbanOrigem());
        transferenciaPOJO.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        return transferenciaPOJO;
    }


    public static TransferenciaPOJOEmis convertingIntoTransferenciaPOJOEmis(Transferencia transferencia, String ibanOrigem)
    {
        TransferenciaPOJOEmis transferenciaPOJOEmis = new TransferenciaPOJOEmis();
        transferenciaPOJOEmis.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaPOJOEmis.setDatahora(transferencia.getDatahora());
        transferenciaPOJOEmis.setDescricao(transferencia.getDescricao());
        transferenciaPOJOEmis.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaPOJOEmis.setMontante(transferencia.getMontante());
        transferenciaPOJOEmis.setEstadoTransferencia(transferencia.getEstadoTransferencia());
        transferenciaPOJOEmis.setFkContaBancariaOrigem(ibanOrigem);
        transferenciaPOJOEmis.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        return  transferenciaPOJOEmis;

        //System.out.println("tranferencias:---::  "+transferencia);
    }

    public void fillingTransactionFields(Transferencia transferencia, String ibanOrigem){


        transferencia.setDatahora(formattingDateTime());
        transferencia.setEstadoTransferencia("Realizado");
        transferencia.setTipoTransferencia("Transferencia Intrabancaria");
        transferencia.setIbanOrigem(ibanOrigem);
    }


    public TransferenciaCustomPOJO convertToTransferenciaCustomPOJO(Transferencia transferencia) {

        TransferenciaCustomPOJO transferenciaCustomPOJO = new TransferenciaCustomPOJO();

        transferenciaCustomPOJO.setDatahora(new Date());
        transferenciaCustomPOJO.setFkContaBancariaOrigem(new BigInteger(userInfo.getUserInfo().get("accountNumber")));
        transferenciaCustomPOJO.setIbanOrigem(userInfo.getUserInfo().get("iban"));
        transferenciaCustomPOJO.setDescricao(transferencia.getDescricao());
        transferenciaCustomPOJO.setMontante(transferencia.getMontante());
        transferenciaCustomPOJO.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaCustomPOJO.setTipoTransferencia("INTERBANCARIA");
        transferenciaCustomPOJO.setEstadoTransferencia("EM PROCESSAMENTO");
        transferenciaCustomPOJO.setCodigoTransferencia(""+this.getCondigoTransferencia());
        transferenciaCustomPOJO.setBancoUdentifier(1003);

        System.out.println(" Descricao: "+transferenciaCustomPOJO.getDescricao());

        System.out.println("transferenciaCustomPOJO: " +transferenciaCustomPOJO.toString());

        //System.out.println("data: " + transferenciaCustomPOJO.getDatahora());

        return transferenciaCustomPOJO;
    }


    public void saveTransferComponent(TransferenciaCustomPOJO transferencia)  {
        Map<String, String> transferenciaItems = new HashMap<>();

        transferenciaItems.put("descricao", transferencia.getDescricao());
        transferenciaItems.put("montante", transferencia.getMontante().toString());
        transferenciaItems.put("ibanDestinatario", transferencia.getIbanDestinatario());
        transferenciaItems.put("datahora","" +
                "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transferencia.getDatahora()));
        transferenciaItems.put("ibanOrigem",transferencia.getIbanOrigem());
        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("tipoTransferencia", transferencia.getTipoTransferencia());
        transferenciaItems.put("estadoTransferencia", transferencia.getEstadoTransferencia());
        transferenciaItems.put("codigoTransferencia", transferencia.getCodigoTransferencia());
        transferenciaItems.put("ibanOrigem",transferencia.getIbanOrigem());
        transferenciaItems.put("bancoUdentifier",""+transferencia.getBancoUdentifier());
        transferenciaComponent.setTransferenciaResponse(transferenciaItems);
        System.out.println("data: " + transferenciaComponent.getTransferenciaResponse().get("datahora"));
    }

    public void builderTransferenciaToTrasnferenciaComponent(Transferencia transferencia, TransferenciaComponent transferenciaComponent )
            throws ParseException {
        ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(transferencia.getIbanOrigem());
        Map<String, String> component = new HashMap<>();
        component.put("descricao",transferencia.getDescricao());
        component.put("montante","" +transferencia.getMontante());
        component.put("ibanDestinatario",transferencia.getIbanDestinatario());
        component.put("datahora", "" +transferencia.getDatahora());
        component.put("fkContaBancariaOrigem",""+contaBancaria.getNumeroDeConta());
        component.put("ibanOrigem",""+transferencia.getIbanOrigem());
        component.put("estadoTransferencia","REALIZADA COM SUCESSO");
        component.put("tipoTransferencia",transferencia.getTipoTransferencia());
        component.put("codigoTransferencia",transferencia.getCodigoTransferencia());
        transferenciaComponent.setTransferenciaResponse(component);
    }

    public LocalDateTime formattingDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }

    public Transferencia criaTransferencia(Transferencia transferencia)
    {
        return this.criar(transferencia);
    }
    public List<Transferencia> findAllDesc(){

        return transferenciaRepository.findAllDesc();
    }

    public List<Transferencia> findAllTransacoesDebitadas(String ibanOrigem){

        return  transferenciaRepository.findAllTransacoesDebitadas(ibanOrigem);
    }

    public List<Transferencia> findAllTransacoesCreditadas(String ibanDestino){

        return transferenciaRepository.findAllTransacoesCreditadas(ibanDestino);
    }

    /*public Transferencia criarTransferencia(Transferencia transferencia, String ibanOrigem){
        fillingTransactionFields(transferencia, ibanOrigem);
        return this.criar(transferencia);
    }

    public String convertingTransferenciaInJson( Transferencia transferenciaCreated){

        TransferenciaPOJO transferenciaPOJO = new TransferenciaPOJO();
        transferenciaPOJO = this.convertingIntoTransferenciaPOJO(transferenciaCreated);

        String transferenciaJson = CustomJsonPojos.criarStrToJson(transferenciaPOJO);

        System.out.println("Data Json" + transferenciaJson);
        return  transferenciaJson;
    }*/

    public String convertingTransferenciaInJsonEmis( Transferencia transferenciaCreated, String ibanOrigem){

        TransferenciaPOJOEmis transferenciaPOJOEmis = new TransferenciaPOJOEmis();
        transferenciaPOJOEmis = this.convertingIntoTransferenciaPOJOEmis(transferenciaCreated, ibanOrigem);

        String transferenciaJsonEmis = CustomJsonPojos.criarStrToJson(transferenciaPOJOEmis);

        System.out.println("Data Json" + transferenciaJsonEmis);
        return  transferenciaJsonEmis;
    }

    public Transferencia buildTransferencia(TransferenciaComponent transferenciaComponent) throws ParseException
    {
        Transferencia transferencia = new Transferencia();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(transferenciaComponent.getTransferenciaResponse().get("datahora"), formatter);

        BigInteger numeroDeConta = new BigInteger(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem"));
        ContaBancaria contaBancaria = contaBancariaRepository.findByNumeroDeConta(numeroDeConta);


       /*if (transferenciaComponent.getTransferenciaResponse().get("bancoUdentifier").equals("1003"))
        {
            BigInteger numeroDeConta = new BigInteger(transferenciaComponent.getTransferenciaResponse().get("fkContaBancariaOrigem"));
            ContaBancaria contaBancaria = contaBancariaRepository.findByNumeroDeConta(numeroDeConta);
        } */
        transferencia.setDescricao(transferenciaComponent.getTransferenciaResponse().get("descricao"));
        transferencia.setMontante(new BigDecimal(transferenciaComponent.getTransferenciaResponse().get("montante")));
        transferencia.setDatahora(localDateTime);
        transferencia.setIbanDestinatario(transferenciaComponent.getTransferenciaResponse().get("ibanDestinatario"));
        transferencia.setIbanOrigem(transferenciaComponent.getTransferenciaResponse().get("ibanOrigem"));

        transferencia.setTipoTransferencia(transferenciaComponent.getTransferenciaResponse().get("tipoTransferencia"));
        transferencia.setEstadoTransferencia("Realizada com sucesso");
        transferencia.setCodigoTransferencia(transferenciaComponent.getTransferenciaResponse().get("codigoTransferencia"));

        if (transferenciaComponent.getTransferenciaResponse().get("bancoUdentifier").equals("1003"))
        {
            transferencia.setOperacao("ENVIADA");
            return transferencia;
        }
        transferencia.setOperacao("RECEBIDA");
        return transferencia;
    }

    public Transferencia transferenciaIntrabancaria(Transferencia transferencia, String ibanOrigem ){

       Transferencia transferenciaCreated;
        fillingTransactionFields(transferencia, ibanOrigem);
        transferenciaCreated = this.criar(transferencia);

        TransferenciaPOJO transferenciaPOJO = convertingIntoTransferenciaPOJO(transferenciaCreated, ibanOrigem);
        String transferenciaJson = CustomJsonPojos.criarStrToJson(transferenciaPOJO);
        String transferenciaJsonEmis = convertingTransferenciaInJsonEmis(transferenciaCreated, ibanOrigem);
        System.out.println("Data Json" + transferenciaJson);

        kafkaTransferenciaProducer.sendMessageTransferenciaIntraBancaria(transferenciaJson.toString());
        kafkaTransferenciaProducer.sendMessageTransferenciaIntraBancariaEmis(transferenciaJsonEmis.toString());

        return transferenciaCreated;
    }





}
