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
    TransferenciaComponent transferenciaComponent;

    @Autowired
    UserInfo userInfo;

    @Autowired
    TransferenciaRepository transferenciaRepository;

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

    public TransferenciaPOJO convertingIntoTransferenciaPOJO(Transferencia transferencia)
    {
        TransferenciaPOJO transferenciaPOJO = new TransferenciaPOJO();
        transferenciaPOJO.setPkTransferencia(transferencia.getPkTransferencia());
        transferenciaPOJO.setDatahora(transferencia.getDatahora());
        transferenciaPOJO.setDescricao(transferencia.getDescricao());
        transferenciaPOJO.setIbanDestinatario(transferencia.getIbanDestinatario());
        transferenciaPOJO.setMontante(transferencia.getMontante());
        transferenciaPOJO.setEstadoTransferencia(transferencia.getEstadoTransferencia());
        transferenciaPOJO.setFkContaBancariaOrigem(transferencia.getFkContaBancariaOrigem().getPkContaBancaria());
        transferenciaPOJO.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        //System.out.println("Transferencia Pojo"+ transferenciaPOJO);

        return transferenciaPOJO;
    }


    public static TransferenciaPOJOEmis convertingIntoTransferenciaPOJOEmis(Transferencia transferencia, String ibanOrigem)
    {
        TransferenciaPOJOEmis TransferenciaPOJOEmis = new TransferenciaPOJOEmis();
        TransferenciaPOJOEmis.setPkTransferencia(transferencia.getPkTransferencia());
        TransferenciaPOJOEmis.setDatahora(transferencia.getDatahora());
        TransferenciaPOJOEmis.setDescricao(transferencia.getDescricao());
        TransferenciaPOJOEmis.setIbanDestinatario(transferencia.getIbanDestinatario());
        TransferenciaPOJOEmis.setMontante(transferencia.getMontante());
        TransferenciaPOJOEmis.setEstadoTransferencia(transferencia.getEstadoTransferencia());
        TransferenciaPOJOEmis.setFkContaBancariaOrigem(ibanOrigem);
        TransferenciaPOJOEmis.setCodigoTransferencia(transferencia.getCodigoTransferencia());

        return TransferenciaPOJOEmis;
    }

    public void fillingTransactionFields(Transferencia transferencia, String ibanOrigem){

        ContaBancaria contaBancaria = contaBancariaService.findContaBancaraByIban(ibanOrigem);
        contaBancaria.getFkCliente().setContaBancariaList(new ArrayList<>());
        contaBancaria.getFkCliente().setUsersList(new ArrayList<>());

        transferencia.setDatahora(formattingDateTime());
        transferencia.setEstadoTransferencia("Realizado");
        transferencia.setTipoTransferencia("Transferencia Intrabancaria");
        transferencia.setFkContaBancariaOrigem(contaBancaria);

        //System.out.println("tranferencias:---::  "+transferencia);
    }

    public TransferenciaCustomPOJO convertToTransferenciaCustomPOJO(Transferencia transferencia) {

        TransferenciaCustomPOJO transferenciaCustomPOJO = new TransferenciaCustomPOJO();

        transferenciaCustomPOJO.setDatahora(new Date());
        transferenciaCustomPOJO.setFkContaBancariaOrigem(new BigInteger(userInfo.getUserInfo().get("accountNumber")));
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
        transferenciaItems.put("fkContaBancariaOrigem",""+transferencia.getFkContaBancariaOrigem());
        transferenciaItems.put("tipoTransferencia", transferencia.getTipoTransferencia());
        transferenciaItems.put("estadoTransferencia", transferencia.getEstadoTransferencia());
        transferenciaItems.put("codigoTransferencia", transferencia.getCodigoTransferencia());
        transferenciaComponent.setTransferenciaResponse(transferenciaItems);
        System.out.println("data: " + transferenciaComponent.getTransferenciaResponse().get("datahora"));
    }

    public LocalDateTime formattingDateTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeFormatted = localDateTime.format(formatter);
        return LocalDateTime.parse(dateTimeFormatted, formatter);
    }

    public List<Transferencia> findAllDesc(){

        return transferenciaRepository.findAllDesc();
    }

    public List<Transferencia> findAllTransacoesDebitadas(Integer fkContaBancariaOrigem){
        return  transferenciaRepository.findAllTransacoesDebitadas(fkContaBancariaOrigem);
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
    }

    public String convertingTransferenciaInJsonEmis( Transferencia transferenciaCreated, String ibanOrigem){

        TransferenciaPOJOEmis transferenciaPOJOEmis = new TransferenciaPOJOEmis();
        transferenciaPOJOEmis = this.convertingIntoTransferenciaPOJOEmis(transferenciaCreated, ibanOrigem);

        String transferenciaJsonEmis = CustomJsonPojos.criarStrToJson(transferenciaPOJOEmis);

        System.out.println("Data Json" + transferenciaJsonEmis);
        return  transferenciaJsonEmis;
    }

    /*public void TransferenciaIntrabancaria(Transferencia transferencia, String ibanOrigem ){

        Transferencia transf = new Transferencia();
        transf = transferencia;

        this.fillingTransactionFields(transf, ibanOrigem);
        criarTransferencia(transf, ibanOrigem);
        TransferenciaPOJO transferenciaPOJO = transferenciaServiceImpl.convertingIntoTransferenciaPOJO(transferencia);

        String transferenciaJson = CustomJsonPojos.criarStrToJson(transferenciaPOJO);
        System.out.println("Data Json" + transferenciaJson);

        transferenciaJsonKafkaProducer.sendMessageTransferenciaIntraBancaria(transferenciaJson.toString());
        transferenciaJsonKafkaProducer.sendMessageTransferenciaIntraBancariaEmis( CustomJsonPojos.criarStrToJson (TransferenciaServiceImpl.convertingIntoTransferenciaPOJOEmis(transferenciaCreated, userInfo.getUserInfo().get("iban"))).toString());


    }*/


}
